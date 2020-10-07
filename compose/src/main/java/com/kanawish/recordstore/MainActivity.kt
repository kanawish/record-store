package com.kanawish.recordstore

import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.*
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageAsset
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.kanawish.recordstore.ui.theming.MyComposedTheme
import com.kanawish.recordstore.model.ProductEditorFlowStore
import com.kanawish.recordstore.model.ProductEditorObservableStore
import com.kanawish.recordstore.state.ProductEditorState.*
import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.ui.theming.typography
import com.kanawish.recordstore.viewmodel.SimpleProductViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import timber.log.Timber
import toothpick.ktp.delegate.inject
import java.lang.IllegalStateException


@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    // Which should you use? [Snark: cue galaxy brain gifs]
    val simpleProductViewModel by viewModels<SimpleProductViewModel>()
    val productEditorObservableStore: ProductEditorObservableStore by inject()
    val productEditorFlowStore: ProductEditorFlowStore by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val stateName: MutableState<String> = mutableStateOf("Android")

        setContent {
            MyComposedTheme {
                Greeting(stateName, ::onButton1, ::onButton2, ::onButton3)
            }
        }
        stateName.value = "Bob"
    }

    fun firebase() {
        val database = Firebase.database
        val ref = database.getReference("bar")
        ref.child(Closed::class.simpleName!!).setValue(Closed::class.simpleName!!)
        ref.child(Editing::class.simpleName!!).setValue(Editing(Product("Hello Product")))
        ref.child(Saving::class.simpleName!!).setValue(Saving(Product("Saving Product")))
        ref.child(Deleting::class.simpleName!!).setValue(Deleting(Product("Deleting Product")))
        ref.child(Error::class.simpleName!!).setValue(Error(Product("Error Product"), "Error Message"))
    }
}

@Composable
fun ProductDemo(product: Product, onDo: () -> Unit, onUndo: () -> Unit) {

}

@Composable
fun Greeting(nameState: State<String>, onButton1: () -> Unit, onButton2: () -> Unit, onButton3: () -> Unit) {
    Timber.i("composing Greeting with ${nameState.value}")

    // Avoid having a different state for every recomposition.
    val name by remember { nameState }

    Column {
        Timber.i("composing Column with ${nameState.value}")
        Text(text = "Hello ${nameState.value}!")
        Button(onClick = onButton1) {
            Text("+1")
        }
        Button(onClick = onButton2) {
            Text("Update \$nameState")
        }
        Button(onClick = onButton3) {
            Text("-1")
        }
    }
}

@Composable
fun ProductCard(
    name: String,
    description: String,
    @DrawableRes imgId: Int,
    sharedCount: Int,
    updateSharedCount: (Int) -> Unit
) {
    val imageAsset: ImageAsset = imageResource(id = imgId)
    val imageModifier = Modifier
        .preferredHeight(120.dp)
        .clip(RoundedCornerShape(18.dp))

    Card {
        Column(modifier = Modifier.padding(16.dp)) {
            Image(
                imageAsset,
                modifier = imageModifier,
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.preferredHeight(12.dp))
            Text(text = name, style = typography.h6)
            Text(
                text = description,
                style = typography.body2,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
            OutlinedButton(
                modifier = Modifier.padding(top = 8.dp),
                onClick = { updateSharedCount(sharedCount + 1) }
            ) {
                Text("👏️ $sharedCount")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposedTheme {
        Greeting(mutableStateOf("Android"), {}, {}, {})
    }
}

@Preview
@Composable
fun ProductPreview() {
    ProductCard(
        name = "Joy Division, 'Unknown Pleasures'",
        description = "Designer Peter Saville's decision to go with pulsar radio waves " +
                "is right up there with Martin Hannett’s spellbinding production in making " +
                "this album a goth classic.\n\n" +
                "Disney's Mickey Mouse shirt parody four decades later only reaffirmed " +
                "its legend.",
        imgId = R.drawable.joy_division_front,
        sharedCount = 0,
        updateSharedCount = { }
    )
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val (sharedCount: Int, updateShared: (Int) -> Unit) = state { 0 }

    MyComposedTheme {
        ScrollableColumn() {
            ProductCard(
                name = "Joy Division, 'Unknown Pleasures'",
                description = "Designer Peter Saville's decision to go with pulsar radio waves " +
                        "is right up there with Martin Hannett’s spellbinding production in making " +
                        "this album a goth classic.\n\n" +
                        "Disney's Mickey Mouse shirt parody four decades later only reaffirmed " +
                        "its legend.",
                imgId = R.drawable.joy_division_front,
                sharedCount = sharedCount,
                updateSharedCount = updateShared
            )
            Divider(color = Color.Black, modifier = Modifier.padding(2.dp))
            ProductCard(
                name = "MadLib Beats | Beat Konducta",
                description = "Otis Jackson Jr. (born October 24, 1973), known professionally as Madlib, is an American DJ, music producer, multi-instrumentalist, and rapper. He is one of the most prolific and critically acclaimed hip hop producers of the 2000s and has collaborated with different hip hop artists, under a variety of pseudonyms, including with MF DOOM (as Madvillain), as well as J Dilla (as Jaylib). Madlib has described himself as a \"DJ first, producer second, and MC last,\"[1][2] and he has done several projects as a DJ, mixer, or remixer.",
                imgId = R.drawable.madlib_movie_scenes_front,
                sharedCount = sharedCount,
                updateSharedCount = updateShared
            )
            Divider(color = Color.Black, modifier = Modifier.padding(2.dp))
            ProductCard(
                name = "Madlib | Shades of Blue",
                description = "Shades of Blue is a remix album by American hip hop musician Madlib over the archives of Blue Note Records. It was released by Blue Note Records on June 24, 2003.\nSam Samuelson of AllMusic gave the album 4 out of 5 stars, saying: \"Intent listening doesn\'t really give much up, but for smooth subconscious grooves, it\'s perfect.\nIn 2014, Paste placed it at number 11 on the \"12 Classic Hip-Hop Albums That Deserve More Attention\" list.",
                imgId = R.drawable.madlib_shades_front,
                sharedCount = sharedCount,
                updateSharedCount = updateShared
            )
        }
    }
}