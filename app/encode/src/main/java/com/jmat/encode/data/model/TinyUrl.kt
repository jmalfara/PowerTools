package com.jmat.encode.data.model

data class TinyUrlCreateRequest(
    val url: String,
    val domain: String,
    val alias: String,
    val tags: String
)

data class TinyUrlCreateResponse(
    val data: String,
    val code: Int,
    val errors: String
)

//"data": {
//    "url": "string",
//    "domain": "string",
//    "alias": "string",
//    "tags": [
//    "string"
//    ],
//    "tiny_url": "string"
//},
//"code": 0,
//"errors": []