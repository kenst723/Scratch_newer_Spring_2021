package app.sato.ken.scratch_newer_spring_2021.function

import android.view.View
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class ShowSnackBar {
    fun show(message: String, action: String, view: View, backColor: Int, textColor: Int){
        val isEmptyFirst = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
        isEmptyFirst.show()
        isEmptyFirst.setAction(action){
            isEmptyFirst.dismiss()
        }
        isEmptyFirst.view.setBackgroundColor(backColor)
        val snackActionView: Button =
            isEmptyFirst.view.findViewById(com.google.android.material.R.id.snackbar_action)
        snackActionView.setTextColor(textColor)
    }
}