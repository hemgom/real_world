package hello.real_world.domain.article.service;

import hello.real_world.domain.article.Article;
import hello.real_world.domain.article.dto.*;
import hello.real_world.domain.article.repository.ArticleRepository;
import hello.real_world.domain.comment.Comment;
import hello.real_world.domain.comment.dto.RequestAddComment;
import hello.real_world.domain.comment.dto.ResponseMultipleComments;
import hello.real_world.domain.comment.dto.ResponseSingleComment;
import hello.real_world.domain.comment.repository.CommentRepository;
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

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final MemberRepository memberRepository;        // 사용자 정보 조회, 프로필 생성
    private final TagRepository tagRepository;              // 태그 저장, 조회
    private final FavoriteRepository favoriteRepository;    // 즐겨찾기 저장, 삭제, 조회
    private final CommentRepository commentRepository;      // 댓글 저장, 삭제, 조회

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
        articleInfo.getAuthor().setFollowing("false");
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
        articleInfo.setUpdateAt(findArticle.getUpdateAt());
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

        for (Comment comment : findArticle.getComments()) {
            if (comment.getArticle() != findArticle) continue;

            commentRepository.delete(comment);
        }
        log.info("삭제할 기사의 댓글 삭제");

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
        log.info("----------------------------------------------------------------");
        log.info("DB 의 모든 태그 조회");
        return new ResponseTags(allTags);
    }

    @Override
    public ResponseSingleComment addComment(RequestAddComment request, String slug, String userEmail) {
        Member loginMember = memberRepository.findByEmail(userEmail);
        Article findArticle = articleRepository.findBySlug(slug);
        log.info("----------------------------------------------------------------");
        log.info("댓글 작성자 조회 및 댓글 작성할 기사 조회");

        Comment comment = Comment.setCommentInfo(request.getComment().getBody(), loginMember.getUsername(), findArticle);
        comment.setCreateAt();
        log.info("댓글 정보 입력 및 작성일 입력");

        commentRepository.save(comment);
        log.info("댓글 저장");

        ResponseSingleComment.CommentInfo commentInfo = commentRepository.getCommentInfo(comment);
        log.info("응답용 댓글 정보 생성");
        commentInfo.setAuthor(memberRepository.getMemberProfile(loginMember));
        log.info("댓글 작성자 프로필 반영");
        commentInfo.getAuthor().setFollowing(memberRepository.checkResult(loginMember, comment.getUsername()));
        log.info("댓글 작성자에 대한 팔로우 상태 반영");

        return new ResponseSingleComment(commentInfo);
    }

    @Override
    public void delComment(String slug, Long commentId, String userEmail) {
        Member loginMember = memberRepository.findByEmail(userEmail);
        Article findArticle = articleRepository.findBySlug(slug);
        log.info("----------------------------------------------------------------");
        log.info("댓글 작성자 조회 및 댓글 삭제할 기사 조회");

        Comment findComment = commentRepository.findByArticleAndId(findArticle, commentId);
        log.info("기사에서 삭제 할 댓글 조회");
        log.info("로그인 사용자 = {}, 댓글 작성자 = {}", loginMember.getUsername(), findComment.getUsername());
        commentRepository.checkAuthor(findComment.getUsername(), loginMember.getUsername());
        log.info("기사의 댓글 작성자가 로그인 사용자인지 확인");

        commentRepository.delete(findComment);
    }

    @Override
    public ResponseMultipleComments getAllCommentsFromArticleNotAuth(String slug) {
        Article findArticle = articleRepository.findBySlug(slug);
        log.info("----------------------------------------------------------------");
        log.info("댓글을 가져올 기사 조회");

        List<ResponseSingleComment.CommentInfo> commentsInfo = commentRepository.getAllCommentsInfo(findArticle);
        log.info("해당 게시물의 모든 댓글을 응답 객체 타입의 리스트로 조회");

        for (ResponseSingleComment.CommentInfo commentInfo : commentsInfo) {
            Comment findComment = commentRepository.findByArticleAndId(findArticle, commentInfo.getId());
            Member commentAuthor = memberRepository.findByUsername(findComment.getUsername());
            commentInfo.setAuthor(memberRepository.getMemberProfile(commentAuthor));
            commentInfo.getAuthor().setFollowing("false");
        }
        log.info("해당 게시물의 모든 댓글 작성자 프로필 적용");

        return new ResponseMultipleComments(commentsInfo);
    }

    @Override
    public ResponseMultipleComments getAllCommentsFromArticle(String slug, String userEmail) {
        Article findArticle = articleRepository.findBySlug(slug);
        Member loginMember = memberRepository.findByEmail(userEmail);
        log.info("----------------------------------------------------------------");
        log.info("댓글을 가져올 기사, 로그인 사용자 조회");

        List<ResponseSingleComment.CommentInfo> commentsInfo = commentRepository.getAllCommentsInfo(findArticle);
        log.info("해당 게시물의 모든 댓글을 응답 객체 타입의 리스트로 조회");

        for (ResponseSingleComment.CommentInfo commentInfo : commentsInfo) {
            Comment findComment = commentRepository.findByArticleAndId(findArticle, commentInfo.getId());
            Member commentAuthor = memberRepository.findByUsername(findComment.getUsername());
            commentInfo.setAuthor(memberRepository.getMemberProfile(commentAuthor));
            commentInfo.getAuthor().setFollowing(memberRepository.checkResult(loginMember, commentAuthor.getUsername()));
        }
        log.info("해당 게시물의 모든 댓글 작성자 프로필 적용");

        return new ResponseMultipleComments(commentsInfo);
    }

    @Override
    public ResponseMultipleArticles findRecentArticlesNotAuth(RequestFindArticles request) {
        List<Article> articles = articleRepository.findRecentArticles(request);
        log.info("----------------------------------------------------------------");
        log.info("요청 파라미터에 따른 기사 조회");

        List<ResponseSingleArticle.ArticleInfo> articlesInfo = new ArrayList<>();
        for (Article article : articles) {
            // 조회한 기사 정보로 응답 객체 생성 및 기사 작성자 프로필 반영
            ResponseSingleArticle.ArticleInfo articleInfo = articleRepository.getArticleInfo(article);
            Member articleAuthor = article.getMember();
            articleInfo.setAuthor(memberRepository.getMemberProfile(articleAuthor));
            articleInfo.getAuthor().setFollowing("false");

            // 작성 완료된 응답 객체 저장
            articlesInfo.add(articleInfo);
        }

        return new ResponseMultipleArticles(articlesInfo);
    }

    @Override
    public ResponseMultipleArticles findRecentArticles(RequestFindArticles request, String userEmail) {
        List<Article> articles = articleRepository.findRecentArticles(request);
        Member loginMember = memberRepository.findByEmail(userEmail);
        log.info("----------------------------------------------------------------");
        log.info("요청 파라미터에 따른 기사 조회 및 로그인 사용자 조회");

        List<ResponseSingleArticle.ArticleInfo> articlesInfo = new ArrayList<>();
        for (Article article : articles) {
            // 조회한 기사 정보로 응답 객체 생성 및 기사 작성자 프로필 반영
            ResponseSingleArticle.ArticleInfo articleInfo = articleRepository.getArticleInfo(article);
            Member articleAuthor = article.getMember();
            articleInfo.setAuthor(memberRepository.getMemberProfile(articleAuthor));
            articleInfo.getAuthor().setFollowing(memberRepository.checkResult(loginMember, articleAuthor.getUsername()));

            // 작성 완료된 응답 객체 저장
            articlesInfo.add(articleInfo);
        }
        log.info("응답 객체 리스트 작성 완료");

        return new ResponseMultipleArticles(articlesInfo);
    }

}