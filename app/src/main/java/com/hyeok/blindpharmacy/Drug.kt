package com.hyeok.blindpharmacy

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty



@JsonIgnoreProperties(ignoreUnknown = true)

data class Drug(
    @JsonProperty("entpName")
    var entpName: String? = "",

    @JsonProperty("itemName")
    var itemName: String? = "",

    @JsonProperty("itemSeq")
    var itemSeq: String? = "",

    @JsonProperty("efcyQesitm")
    var efcyQesitm: String? = "",

    @JsonProperty("useMethodQesitm")
    var useMethodQesitm: String? = "",

    @JsonProperty("atpnWarnQesitm")
    var atpnWarnQesitm: String? = "",

    @JsonProperty("atpnQesitm")
    var atpnQesitm: String? = "",

    @JsonProperty("intrcQesitm")
    var intrcQesitm: String? = "",

    @JsonProperty("seQesitm")
    var seQesitm: String? = "",

    @JsonProperty("depositMethodQesitm")
    var depositMethodQesitm: String? = "",

    @JsonProperty("openDe")
    var openDe: String? = "",

    @JsonProperty("updateDe")
    var updateDe: String? = "",

    @JsonProperty("itemImage")
    var itemImage: String? = ""
)
