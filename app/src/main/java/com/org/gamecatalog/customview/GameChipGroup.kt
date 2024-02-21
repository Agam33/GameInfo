package com.org.gamecatalog.customview

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.shape.ShapeAppearanceModel
import com.org.gamecatalog.R

class GameChipGroup: ChipGroup {

  private val chipContainer = HashMap<Int, Chip>()
  private var current = -1

  private var setChildColorBackground: ColorStateList? = context.getColorStateList(R.color.bg_chip_state)
  private var setChildTextColor: ColorStateList? = context.getColorStateList(R.color.white)

  var onItemChipClickListener: OnItemChipClickListener? = null

  constructor(context: Context?) : super(context)
  constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
  constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
    context,
    attrs,
    defStyleAttr
  )

  init {
    val firstChip = createChip(ChipModel(-1, "All", "All"))
    chipContainer[firstChip.id] = firstChip
    addView(firstChip)
    chipContainer[current]?.isChecked = true
    isSingleSelection = true
  }

  private fun addChipChild(chipModel: ChipModel) {
    if(chipContainer.containsKey(chipModel.id)) return
    val newChip = createChip(chipModel)
    chipContainer[chipModel.id] = newChip
    addView(newChip)
  }

  private fun createChip(chipModel: ChipModel): Chip {
    return Chip(context).apply {
      shapeAppearanceModel = ShapeAppearanceModel()
        .toBuilder()
        .setAllCornerSizes(24f)
        .build()
      text = chipModel.title
      chipBackgroundColor = setChildColorBackground
      chipStrokeWidth = 0f
      setOnClickListener {
        current = chipModel.id
        onItemChipClickListener?.onChipClicked(chipModel)
      }
      setTextColor(setChildTextColor)
      isCheckable = true
      isFocusable = true
      isChecked = false
      textAlignment = TEXT_ALIGNMENT_CENTER
    }
  }

  fun ifFirstChip(title: String): String? =
    if(title == FIRST_CHIP_TITLE) null else title

  fun refreshCheck() {
    chipContainer[current]?.isChecked = false
    current = -1
    chipContainer[current]?.isChecked = true
  }

  fun submit(chipModel: ChipModel) {
    addChipChild(chipModel)
  }

  fun submit(chipModels: List<ChipModel>) {
    for(chipModel in chipModels) {
        addChipChild(chipModel)
    }
  }

  data class ChipModel(val id: Int, val title: String, val slug: String)

  fun toChipModel(id: Int, title: String, slug: String): ChipModel =
    ChipModel(id, title, slug)

  interface OnItemChipClickListener {
    fun onChipClicked(chipModel: ChipModel)
  }

  companion object {
    const val FIRST_CHIP_TITLE = "All"
  }
}