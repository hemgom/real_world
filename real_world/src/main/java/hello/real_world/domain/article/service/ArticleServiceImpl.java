package hello.real_world.domain.article.service;

import hello.real_world.domain.article.Article;
import hello.real_world.domain.article.dto.RequestAddArticle;
import hello.real_world.domain.article.dto.RequestUpdateArticle;
import hello.real_world.domain.article.dto.ResponseSingleArticle;
import hello.real_world.domain.article.repository.ArticleRepository;
import hello.real_world.domain.member.Member;
import hello.real_world.domain.member.repository.MemberRepository;
import hello.real_world.domain.tag.Tag;
import hello.real_world.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final TagRepository tagRepository;          // 태그 목록 사용
    private final MemberRepository memberRepository;    // 게시글 작성자 프로필 사용하기 위해

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

        ResponseSingleArticle.ArticleInfo articleInfo = articleRepository.getArticleInfoNotAuth(findArticle);
        log.info("기사 정보 응답 객체로 가져오기");

        articleInfo.setAuthor(memberRepository.getMemberProfile(findArticle.getMember()));
        log.info("기사 작성자 프로필 입력");

        return new ResponseSingleArticle(articleInfo);
    }

    @Override
    public ResponseSingleArticle getArticle(String slug, String userEmail) {
        Article findArticle = articleRepository.findBySlug(slug);
        log.info("----------------------------------------------------------------");
        log.info("요청 정보로 기사 조회");

        ResponseSingleArticle.ArticleInfo articleInfo = articleRepository.getArticleInfoNotAuth(findArticle);
        articleInfo.setAuthor(memberRepository.getMemberProfile(findArticle.getMember()));
        // 미완성 즐겨찾기 추가 기능 구현 후 추가 작성 필요
        log.info("기사 정보 가져오기 완료");

        return null;
    }

    public ResponseSingleArticle updateArticle(RequestUpdateArticle request,
                                               String slug, String userEmail) {
        Article findArticle = articleRepository.findBySlug(slug);
        findArticle.updateArticle(request.getArticle());
        findArticle.setUpdateAt();

        // 미완성 즐겨찾기 추가 기능 구현 후 추가 작성 필요

        return null;
    }

}
