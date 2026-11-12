package org.sopt.meemong.domain.designer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.meemong.domain.designer.dto.response.DesignerDetailResponse;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.http.ResponseEntity;

@Tag(
        name = "게시물 상세",
        description = "게시물 상세 페이지 관련 API"
)
public interface DesignerDetailApi {

    @Operation(
            summary = "디자이너 정보 조회",
            description = "공고 ID를 기준으로 디자이너의 포트폴리오 이미지와 매장 주소 및 매장 이미지를 조회합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "디자이너 정보 조회 성공",
                    useReturnTypeSchema = true,
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(
                                    name = "SUCCESS",
                                    value = """
                                            {
                                              "code": "SUCCESS",
                                              "data": {
                                                "portfolioImageUrls": [
                                                  "https://example.com/portfolio1.jpg",
                                                  "https://example.com/portfolio2.jpg",
                                                  "https://example.com/portfolio3.jpg"
                                                ],
                                                "shopAddress": "경기 고양시 덕성로 123",
                                                "shopImageUrls": [
                                                  "https://example.com/shop1.jpg",
                                                  "https://example.com/shop2.jpg"
                                                ]
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
    ResponseEntity<CommonResponse<DesignerDetailResponse>> getDesignerDetail(
            @Parameter(
                    description = "조회할 공고 ID (양수)",
                    example = "1",
                    required = true
            )
            Long postId
    );
}
