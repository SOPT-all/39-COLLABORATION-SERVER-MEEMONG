package org.sopt.meemong.domain.match.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.meemong.domain.match.dto.response.GeneralPostListResponse;
import org.sopt.meemong.domain.match.dto.response.RecommendedPostListResponse;
import org.sopt.meemong.global.response.CommonResponse;

@Tag(name = "빠른매칭", description = "빠른매칭 공고 목록 조회 API")
public interface MatchApi {

    @Operation(
            summary = "미몽 추천 공고 목록 조회",
            description = "추천 공고를 작성일 최신순으로 최대 4건 조회합니다. 공고가 없으면 빈 목록을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "추천 공고 목록 조회 성공",
                    useReturnTypeSchema = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "SUCCESS",
                                    value = """
                                        {
                                          "code": "SUCCESS",
                                          "data": {
                                            "posts": [
                                              {
                                                "postId": 4,
                                                "title": "C컬, S컬펌 모델 받고 있습니다!💛",
                                                "thumbnailUrl": "https://example.com/post-4.jpg",
                                                "priceType": "NEGOTIABLE",
                                                "displayPrice": 15000,
                                                "region": "서울 서초구",
                                                "availableTimes": ["WEEKDAY_MORNING", "WEEKDAY_AFTERNOON"],
                                                "treatmentTypes": ["CUT", "PERM", "STRAIGHT"],
                                                "isLiked": false,
                                                "createdAt": "2026-11-26T21:10:00+09:00"
                                              }
                                            ]
                                          }
                                        }
                                        """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부의 예상하지 못한 오류",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "INTERNAL_SERVER_ERROR",
                                    value = """
                                        {"code": "INTERNAL_SERVER_ERROR", "data": null}
                                        """
                            )
                    )
            )
    })
    CommonResponse<RecommendedPostListResponse> getRecommendedPosts();

    @Operation(
            summary = "일반 공고 목록 조회",
            description = "일반 공고를 작성일 최신순으로 최대 4건 조회합니다. 공고가 없으면 빈 목록을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "일반 공고 목록 조회 성공",
                    useReturnTypeSchema = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "SUCCESS",
                                    value = """
                                            {
                                              "code": "SUCCESS",
                                              "data": {
                                                "posts": [
                                                  {
                                                    "postId": 8,
                                                    "title": "레이어드 시술합니다!",
                                                    "designerName": "아크미서하",
                                                    "designerProfileImageUrl": "https://example.com/designer.png",
                                                    "priceType": "FREE",
                                                    "displayPrice": null,
                                                    "region": "서울 강남구",
                                                    "availableTimes": ["WEEKDAY_MORNING"],
                                                    "treatmentTypes": ["CUT"],
                                                    "isLiked": false,
                                                    "createdAt": "2026-11-26T18:00:00+09:00"
                                                  }
                                                ]
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부의 예상하지 못한 오류",
                    useReturnTypeSchema = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "INTERNAL_SERVER_ERROR",
                                    value = """
                                            {
                                              "code": "INTERNAL_SERVER_ERROR",
                                              "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    CommonResponse<GeneralPostListResponse> getGeneralPosts();
}
