package org.sopt.meemong.domain.post.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.meemong.domain.post.dto.response.PostDetailResponse;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "게시물 상세", description = "게시물 내용 상세 조회 API")
public interface PostDetailApi {
    @Operation(
            summary = "게시물 내용 상세 조회",
            description = "공고 ID를 기준으로 공고 기본 정보, 가격 및 시술 정보, 디자이너 기본 정보와 본문을 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "게시물 내용 상세 조회 성공",
                    useReturnTypeSchema = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "SUCCESS",
                                    value = """
                                            {
                                              "code": "SUCCESS",
                                              "data": {
                                                "postId": 1,
                                                "title": "뭘 해야 할지 모르겠다면?",
                                                "postImageUrls": [
                                                  "https://example.com/post1.jpg",
                                                  "https://example.com/post2.jpg"
                                                ],
                                                "isLiked": true,
                                                "priceType": "REVIEW",
                                                "discountRate": 50.00,
                                                "originalPrice": 35000,
                                                "displayPrice": 17500,
                                                "treatmentTypes": ["CUT"],
                                                "signatures": ["CUT"],
                                                "shootingRanges": ["PHOTO"],
                                                "availableTimes": ["WEEKDAY_MORNING"],
                                                "shopName": "아벨라움",
                                                "designerId": 1,
                                                "designerName": "승환",
                                                "designerProfileImageUrl": "https://example.com/profile.jpg",
                                                "region": "경기 고양시",
                                                "completedTreatmentCount": 12,
                                                "content": "고객님께 어울리는 커트 스타일을 제안합니다.",
                                                "createdAt": "2026-11-07T16:17:00+09:00"
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "유효하지 않은 공고 ID",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "INVALID_POST_ID",
                                    value = """
                                            {
                                              "code": "INVALID_POST_ID",
                                              "data": null
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "해당 공고를 찾을 수 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "POST_NOT_FOUND",
                                    value = """
                                            {
                                              "code": "POST_NOT_FOUND",
                                              "data": null
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
                                            {
                                              "code": "INTERNAL_SERVER_ERROR",
                                              "data": null
                                            }
                                            """
                            )
                    )
            )
    })
    ResponseEntity<CommonResponse<PostDetailResponse>> getPostDetail(
            @Parameter(
                    description = "조회할 공고 ID (양수)",
                    example = "1",
                    required = true
            )
            Long postId
    );
}
