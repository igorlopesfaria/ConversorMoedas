package br.com.sympla.conversormoedas.common

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.github.siyamed.shapeimageview.CircularImageView
import java.lang.ref.WeakReference
import kotlin.reflect.KProperty

fun <T : View> viewBind(@IdRes idRes: Int) =
    ViewBindDelegate<T>(idRes = idRes, required = true)
class ViewBindDelegate<out T>(
    @IdRes private val idRes: Int,
    private val required: Boolean
) {

    private var view: View? = null

    operator fun getValue(thisRef: AppCompatActivity, property: KProperty<*>): T {
        view?.let { if (!it.isAttachedToWindow) view = null }
        return findView(property) {
           thisRef.findViewById(idRes)
        }
    }

    operator fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        view?.let { if (!it.isAttachedToWindow) view = null }
        return findView(property) {
            thisRef.view?.findViewById(idRes)
        }
    }

    operator fun getValue(thisRef: View, property: KProperty<*>): T {
        return findView(property) {
           thisRef.findViewById(idRes)
        }
    }

    operator fun getValue(thisRef: RecyclerView.ViewHolder, property: KProperty<*>): T {
        return findView(property) {
            thisRef.itemView.findViewById(idRes)
        }
    }

    @Suppress("UNCHECKED_CAST")
    private inline fun findView(property: KProperty<*>, crossinline initializer: () -> View?): T {
        view = (view ?: initializer.invoke())
        if (required && view == null) {
            throw IllegalStateException("View ID $idRes for '${property.name}' not found on view.")
        }
        return view as T
    }
}
