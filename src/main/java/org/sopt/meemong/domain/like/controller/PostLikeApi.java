package org.sopt.meemong.domain.like.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.sopt.meemong.domain.like.dto.response.PostLikeResponse;
import org.sopt.meemong.global.response.CommonResponse;
import org.springframework.http.ResponseEntity;

@Tag(name = "공고 찜", description = "공고 상단 하트 버튼의 찜 등록 및 취소 API")
public interface PostLikeApi {

    @Operation(
            summary = "공고 찜 등록",
            description = "공고를 찜 상태로 변경합니다. 이미 찜한 공고에 동일한 요청이 들어오면 기존 상태를 유지합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "찜 등록 성공",
                    useReturnTypeSchema = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "SUCCESS",
                                    value = """
                                            {
                                              "code": "SUCCESS",
                                              "data": {
                                                "isLiked": true
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "공고 ID가 Long 범위 내의 양수가 아니거나 문자열인 경우",
                    useReturnTypeSchema = true,
                    content = @Content(
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
                    description = "해당 ID를 가진 공고가 존재하지 않는 경우",
                    useReturnTypeSchema = true,
                    content = @Content(
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
                    useReturnTypeSchema = true,
                    content = @Content(
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
    ResponseEntity<CommonResponse<PostLikeResponse>> like(
            @Parameter(
                    description = "공고 ID",
                    required = true,
                    example = "8",
                    schema = @Schema(type = "integer", format = "int64", minimum = "1")
            )
            Long postId
    );

    @Operation(
            summary = "공고 찜 취소",
            description = "공고의 찜을 취소합니다. 동일한 요청이 반복되어도 취소 상태를 유지합니다."
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "찜 취소 성공",
                    useReturnTypeSchema = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "SUCCESS",
                                    value = """
                                            {
                                              "code": "SUCCESS",
                                              "data": {
                                                "isLiked": false
                                              }
                                            }
                                            """
                            )
                    )
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = "공고 ID가 Long 범위 내의 양수가 아니거나 문자열인 경우",
                    useReturnTypeSchema = true,
                    content = @Content(
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
                    description = "해당 ID를 가진 공고가 존재하지 않는 경우",
                    useReturnTypeSchema = true,
                    content = @Content(
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
                    useReturnTypeSchema = true,
                    content = @Content(
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
    ResponseEntity<CommonResponse<PostLikeResponse>> unlike(
            @Parameter(
                    description = "공고 ID",
                    required = true,
                    example = "8",
                    schema = @Schema(type = "integer", format = "int64", minimum = "1")
            )
            Long postId
    );
}
