package co.csadev.fetchhiring

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun ItemRow(
    id: Int,
    listId: Int,
    name: String?
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(colorResource(id = R.color.background_color))  // Use background color from XML
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(dimensionResource(id = R.dimen.row_height))
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_horizontal),
                    vertical = dimensionResource(id = R.dimen.padding_vertical)
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // Name
            Text(
                text = "$id",
                fontWeight = FontWeight.Bold,
                color = colorResource(id = R.color.name_text_color),  // Text color from colors.xml
                fontSize = dimensionResource(id = R.dimen.name_text_size).value.sp,  // Font size from dimens.xml
                lineHeight = dimensionResource(id = R.dimen.name_line_height).value.sp,  // Line height from dimens.xml
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.name_spacing)))

            // Item Name
            Text(
                text = (name ?: "").ifBlank { stringResource(id = R.string.item_name_none) },
                fontWeight = FontWeight.Normal,
                color = colorResource(id = R.color.name_text_color),  // Name text color from colors.xml
                fontSize = dimensionResource(id = R.dimen.name_text_size).value.sp,  // Font size from dimens.xml
                lineHeight = dimensionResource(id = R.dimen.name_line_height).value.sp,  // Line height from dimens.xml
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        HorizontalDivider(
            color = colorResource(id = R.color.divider_color),  // Divider color from colors.xml
            thickness = dimensionResource(id = R.dimen.divider_thickness)  // Divider thickness from dimens.xml
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewItemRow() {
    // Preview with placeholder data and the default avatar
    ItemRow(
        id = 1,  // Item ID
        listId = 2,  // List ID
        name = stringResource(id = R.string.name_placeholder)  // Placeholder name from strings.xml
    )
}

