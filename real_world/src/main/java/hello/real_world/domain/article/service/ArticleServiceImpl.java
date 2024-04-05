package hello.real_world.domain.article.service;

import hello.real_world.domain.article.Article;
import hello.real_world.domain.article.dto.RequestAddArticle;
import hello.real_world.domain.article.dto.RequestUpdateArticle;
import hello.real_world.domain.article.dto.ResponseSingleArticle;
import hello.real_world.domain.article.repository.ArticleRepository;
import hello.real_world.domain.favorite.Favorite;
import hello.real_world.domain.favorite.repository.FavoriteRepository;
import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.repository.MemberRepository;
import hello.real_world.domain.tag.Tag;
import hello.real_world.domain.tag.dto.ResponseTags;
import hello.real_world.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;              // 태그 목록 사용
    private final MemberRepository memberRepository;        // 게시글 작성자 프로필 사용하기 위해
    private final FavoriteRepository favoriteRepository;    // 게시글 즐겨찾기 정보 사용

    @Override
    public ResponseSingleArticle createArticle(RequestAddArticle request, String userEmail) {
        Member findMember = memberRepository.findByEmail(userEmail);
        RequestAddArticle.CreateInfo createInfo = RequestAddArticle.CreateInfo.setCreateInfo(request);
        Article article = Article.setArticleInfo(createInfo, findMember);
        log.info("----------------------------------------------------------------");
        log.info("요청 정보로 기사 생성");

        article.setCreateAt();
        log.info("기사 작성일 입력");

        articleRepository.save(article);
        log.info("기사 등록 완료");

        for (String tagName : createInfo.getTagList()) {
            Tag tag = Tag.setTagInfo(tagName, article);
            tagRepository.save(tag);
        }
        log.info("요청 태그 저장 완료");

        ResponseSingleArticle.ArticleInfo articleInfo = ResponseSingleArticle.ArticleInfo.setArticleInfo(createInfo);
        articleInfo.setCreateAt(article.getCreateAt());
        articleInfo.setFavoriteCount(article.getFavoriteCount());
        articleInfo.setAuthor(memberRepository.getMemberProfile(findMember));
        log.info("응답 작성 완료");

        return new ResponseSingleArticle(articleInfo);
    }

    @Override
    public ResponseSingleArticle getArticleNotAuth(String slug) {
        Article findArticle = articleRepository.findBySlug(slug);
        log.info("----------------------------------------------------------------");
        log.info("요청 정보로 기사 조회");

        ResponseSingleArticle.ArticleInfo articleInfo = articleRepository.getArticleInfo(findArticle);
        log.info("기사 정보 응답 객체로 가져오기");
        articleInfo.setAuthor(memberRepository.getMemberProfile(findArticle.getMember()));
        log.info("기사 작성자 프로필 입력");
        articleInfo.getAuthor().setFollowing("false");
        log.info("기사 작성자 팔로우 상태 반영");

        return new ResponseSingleArticle(articleInfo);
    }

    @Override
    public ResponseSingleArticle getArticle(String slug, String userEmail) {
        Member loginMember = memberRepository.findByEmail(userEmail);
        log.info("----------------------------------------------------------------");
        log.info("로그인 사용자 조회");
        
        Article findArticle = articleRepository.findBySlug(slug);
        log.info("요청 정보로 기사 조회");

        ResponseSingleArticle.ArticleInfo articleInfo = articleRepository.getArticleInfo(findArticle);
        log.info("응답용 기사 정보 생성");
        articleInfo.setFavorite(favoriteRepository.checkFavoriteStatus(findArticle, loginMember.getUsername()));
        log.info("로그인 사용자의 해당 기사 즐겨찾기 여부 확인");
        articleInfo.setAuthor(memberRepository.getMemberProfile(findArticle.getMember()));
        log.info("기사 작성자 프로필 반영");
        articleInfo.getAuthor().setFollowing(memberRepository.checkResult(loginMember, findArticle.getMember().getUsername()));
        log.info("기사 작성자 팔로우 상태 반영");

        return new ResponseSingleArticle(articleInfo);
    }

    @Override
    public ResponseSingleArticle updateArticle(RequestUpdateArticle request,
                                               String slug, String userEmail) {
        Member loginMember = memberRepository.findByEmail(userEmail);
        Article findArticle = articleRepository.findBySlug(slug);
        log.info("----------------------------------------------------------------");
        log.info("기사, 로그인 사용자 정보 조회");

        findArticle.updateArticle(request.getArticle());
        log.info("기사 수정 정보 입력");

        findArticle.setUpdateAt();
        log.info("기사 수정일 입력");

        articleRepository.save(findArticle);
        log.info("수정 기사 업데이트");

        ResponseSingleArticle.ArticleInfo articleInfo = articleRepository.getArticleInfo(findArticle);
        articleInfo.setFavorite(favoriteRepository.checkFavoriteStatus(findArticle, loginMember.getUsername()));
        articleInfo.setAuthor(memberRepository.getMemberProfile(findArticle.getMember()));
        articleInfo.getAuthor().setFollowing(memberRepository.checkResult(loginMember, findArticle.getMember().getUsername()));

        return new ResponseSingleArticle(articleInfo);
    }

    @Override
    public void delArticle(String slug) {
        Article findArticle = articleRepository.findBySlug(slug);
        log.info("----------------------------------------------------------------");
        log.info("로그인 사용자 조회, 기사 조회");

        for (Tag tag : findArticle.getTagList()) {
            if (tag.getArticle() != findArticle) continue;

            tagRepository.delete(tag);
        }
        log.info("삭제할 기사의 태그 삭제");

        for (Favorite favorite : findArticle.getFavorites()) {
            if (favorite.getArticle() != findArticle) continue;

            favoriteRepository.delete(favorite);
        }
        log.info("삭제할 기사에 대한 즐겨찾기 삭제");

        articleRepository.delete(findArticle);
        log.info("기사 삭제 완료");
    }

    @Override
    public ResponseSingleArticle addFavoriteArticle(String slug, String userEmail) {
        Member loginMember = memberRepository.findByEmail(userEmail);
        log.info("----------------------------------------------------------------");
        log.info("로그인 사용자 조회");

        Article findArticle = articleRepository.findBySlug(slug);
        log.info("요청 slug 로 기사 조회");

        Favorite favorite = favoriteRepository.addFavorite(findArticle, loginMember.getUsername());
        favoriteRepository.save(favorite);
        log.info("즐겨찾기 정보 업데이트");

        findArticle.favoriteCountIncrease();
        articleRepository.save(findArticle);
        log.info("즐겨찾기 카운트 업데이트");

        ResponseSingleArticle.ArticleInfo articleInfo = articleRepository.getArticleInfo(findArticle);
        log.info("응답용 기사 정보 생성");
        articleInfo.setFavorite("true");
        log.info("기사 즐겨찾기 상태 수정");
        articleInfo.setAuthor(memberRepository.getMemberProfile(findArticle.getMember()));
        log.info("기사 작성자 프로필 반영");
        articleInfo.getAuthor().setFollowing(memberRepository.checkResult(loginMember, findArticle.getMember().getUsername()));
        log.info("기사 작성자 팔로우 상태 반영");

        return new ResponseSingleArticle(articleInfo);
    }

    @Override
    public ResponseSingleArticle delFavoriteArticle(String slug, String userEmail) {
        Member loginMember = memberRepository.findByEmail(userEmail);
        log.info("----------------------------------------------------------------");
        log.info("로그인 사용자 조회");

        Article findArticle = articleRepository.findBySlug(slug);
        log.info("요청 slug 로 기사 조회");

        Favorite favorite = favoriteRepository.findByArticleAndUsername(findArticle, loginMember.getUsername());
        favoriteRepository.delete(favorite);
        log.info("즐겨찾기 삭제");

        ResponseSingleArticle.ArticleInfo articleInfo = articleRepository.getArticleInfo(findArticle);
        log.info("응답용 기사 정보 생성");
        articleInfo.setFavorite("false");
        log.info("즐겨찾기 상태 수정");
        articleInfo.setAuthor(memberRepository.getMemberProfile(findArticle.getMember()));
        log.info("기사 작성자 프로필 반영");
        articleInfo.getAuthor().setFollowing(memberRepository.checkResult(loginMember, findArticle.getMember().getUsername()));
        log.info("기사 작성자 팔로우 상태 반영");

        return new ResponseSingleArticle(articleInfo);
    }

    @Override
    public ResponseTags getAllTags() {
        List<String> allTags = tagRepository.findAllTags();
        return new ResponseTags(allTags);
    }

}