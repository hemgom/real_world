package hello.real_world.domain.article.service;

import hello.real_world.domain.article.dto.*;
import hello.real_world.domain.comment.dto.RequestAddComment;
import hello.real_world.domain.comment.dto.ResponseMultipleComments;
import hello.real_world.domain.comment.dto.ResponseSingleComment;
import hello.real_world.domain.tag.dto.ResponseTags;

public interface ArticleService {

    // 기사 생성
    ResponseSingleArticle createArticle(RequestAddArticle request, String userEmail);

    // (인증 없이) 기사 조회
    ResponseSingleArticle getArticleNotAuth(String slug);

    // 기사 조회 (인증)
    ResponseSingleArticle getArticle(String slug, String userEmail);

    // 기사 수정 (업데이트)
    ResponseSingleArticle updateArticle(RequestUpdateArticle request, String slug, String userEmail);

    // 기사 삭제
    void delArticle(String slug);

    // 즐겨찾기 추가
    ResponseSingleArticle addFavoriteArticle(String slug, String userEmail);

    // 즐겨찾기 삭제
    ResponseSingleArticle delFavoriteArticle(String slug, String userEmail);

    // 모든 태그 반환 (리스트)
    ResponseTags getAllTags();

    // 기사 댓글 추가
    ResponseSingleComment addComment(RequestAddComment request, String slug, String userEmail);

    // 기사 댓글 제거
    void delComment(String slug, Long commentId, String userEmail);

    // 기사의 모든 댓글 조회 (인증X)
    ResponseMultipleComments getAllCommentsFromArticleNotAuth(String slug);

    // 기사의 모든 댓글 조회 (인증O)
    ResponseMultipleComments getAllCommentsFromArticle(String slug, String userEmail);

    // 최근 기사 목록 조회 (인증X)
    ResponseMultipleArticles findRecentArticlesNotAuth(RequestFindArticles request);

    // 최근 기사 목록 조회 (인증O)
    ResponseMultipleArticles findRecentArticles(RequestFindArticles request, String userEmail);

    // 피드 기사 조회 (인증O)
    ResponseMultipleArticles makeFeedArticles(RequestFindArticles request, String userEmail);

}
