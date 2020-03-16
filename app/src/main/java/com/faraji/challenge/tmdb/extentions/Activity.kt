package  com.faraji.challenge.tmdb.extentions

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.util.TypedValue
import android.view.Window
import android.widget.LinearLayout
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.faraji.challenge.tmdb.R
import com.faraji.challenge.tmdb.ui.dialogfragment.MessageDialogFragment
import com.faraji.challenge.tmdb.ui.dialogfragment.MessageDialogFragment.Companion.NETWORK_ERROR_DIALOG_TAG
import java.util.*


@Synchronized
fun AppCompatActivity.messageDialog(
    message: MessageDialogFragment.Message,
    tag: String? = null,
    cancelable: Boolean = true,
    onclick: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) = MessageDialogFragment.newInstance(message, cancelable, onclick, onCancel)
    .show(supportFragmentManager, tag ?: "MessageDialog")

fun AppCompatActivity.messageDialog(
    message: String,
    buttonText: String? = null,
    image: Int? = null,
    tag: String? = null,
    cancelable: Boolean = true,
    onClick: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) = messageDialog(
    MessageDialogFragment.Message(message, buttonText, image),
    tag,
    cancelable,
    onClick,
    onCancel
)

fun AppCompatActivity.networkErrorDialog(
    message: String,
    onRetry: (() -> Unit)? = null,
    onCancel: (() -> Unit)? = null
) {
    messageDialog(
        message,
        getString(R.string.retry),
        null,
        NETWORK_ERROR_DIALOG_TAG,
        true,
        onRetry, onCancel

    )
}


inline fun Activity.numberPicker(
    crossinline onYearSelected: (year: Int) -> Unit
) {
    val alertDialog = android.app.AlertDialog.Builder(this)
    alertDialog.setTitle("Select Year")
    alertDialog.setMessage("")
    val numberPicker = NumberPicker(this)
    val currentYear = Calendar.getInstance().get(Calendar.YEAR)
    val displayValues =
        (1900..currentYear).map { it.toString() }.toMutableList().apply { add(0, "All") }
    numberPicker.minValue = 1899
    numberPicker.maxValue = currentYear
    numberPicker.displayedValues = displayValues.toTypedArray()
    val lp = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.MATCH_PARENT
    )
    alertDialog.setView(numberPicker.apply { layoutParams = lp })
    alertDialog.setPositiveButton("Select") { dialog, which ->
        onYearSelected(numberPicker.value)
    }
    alertDialog.setNegativeButton("Cancel") { dialog, which ->
        dialog.dismiss()
    }
    alertDialog.show()
}