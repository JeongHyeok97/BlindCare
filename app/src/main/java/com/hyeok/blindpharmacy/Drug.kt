package com.hyeok.blindpharmacy

import com.fasterxml.jackson.annotation.JsonProperty

data class Drug(
    @JsonProperty("resultCode")
    val resultCode: String,

    @JsonProperty("resultMsg")
    val resultMsg: String,

    @JsonProperty("numOfRows")
    val numOfRows: Int?,

    @JsonProperty("pageNo")
    val pageNo: Int?,

    @JsonProperty("totalCount")
    val totalCount: Long?,

    @JsonProperty("entpName")
    val entpName: String?,

    @JsonProperty("itemName")
    val itemName: String?,

    @JsonProperty("itemSeq")
    val itemSeq: String?,

    @JsonProperty("efcyQesitm")
    val efcyQesitm: String?,

    @JsonProperty("useMethodQesitm")
    val useMethodQesitm: String?,

    @JsonProperty("atpnWarnQesitm")
    val atpnWarnQesitm: String?,

    @JsonProperty("atpnQesitm")
    val atpnQesitm: String?,

    @JsonProperty("intrcQesitm")
    val intrcQesitm: String?,

    @JsonProperty("seQesitm")
    val seQesitm: String?,

    @JsonProperty("depositMethodQesitm")
    val depositMethodQesitm: String?,

    @JsonProperty("openDe")
    val openDe: String?,

    @JsonProperty("updateDe")
    val updateDe: String?,

    @JsonProperty("itemImage")
    val itemImage: String?
)
