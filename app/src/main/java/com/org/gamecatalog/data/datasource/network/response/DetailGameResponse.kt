package com.org.gamecatalog.data.datasource.network.response

import com.google.gson.annotations.SerializedName

data class DetailGameResponse(
  @field:SerializedName("id") val id: Int,
  @field:SerializedName("slug") val slug: String?,
  @field:SerializedName("name") val name: String?,
  @field:SerializedName("name_original") val nameOriginal: String?,
  @field:SerializedName("description_raw") val description: String?,
  @field:SerializedName("background_image") val backgroundImage: String?,
  @field:SerializedName("rating") val rating: Double?,
  @field:SerializedName("platforms") val platforms: List<GamePlatformResponse>?,
  @field:SerializedName("ratings") val ratings: List<RatingResponse>?,
  @field:SerializedName("genres") val genres: List<GenreResponse>?,
  @field:SerializedName("website") val websiteUrl: String?,
  @field:SerializedName("publishers") val publishers: List<PublisherResponse>?
)