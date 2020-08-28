package com.kanawish.recordstore.compose

import android.os.Bundle
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollableColumn
import androidx.compose.foundation.Text
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.runtime.state
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
import com.kanawish.mycomposed.ui.MyComposedTheme
import com.kanawish.recordstore.compose.ui.typography

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposedTheme {
                Greeting("Android")
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    val bar = state { 0 }
    val foo = remember { }
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MyComposedTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true)
@Composable
fun ProductListPreview() {
    val (sharedCount: Int, updateShared: (Int) -> Unit) = state { 0 }

    MyComposedTheme {

        ScrollableColumn() {
            Product(
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
            Product(
                name = "MadLib Beats | Beat Konducta",
                description = "Otis Jackson Jr. (born October 24, 1973), known professionally as Madlib, is an American DJ, music producer, multi-instrumentalist, and rapper. He is one of the most prolific and critically acclaimed hip hop producers of the 2000s and has collaborated with different hip hop artists, under a variety of pseudonyms, including with MF DOOM (as Madvillain), as well as J Dilla (as Jaylib). Madlib has described himself as a \"DJ first, producer second, and MC last,\"[1][2] and he has done several projects as a DJ, mixer, or remixer.",
                imgId = R.drawable.madlib_movie_scenes_front,
                sharedCount = sharedCount,
                updateSharedCount = updateShared
            )
            Divider(color = Color.Black, modifier = Modifier.padding(2.dp))
            Product(
                name = "Madlib | Shades of Blue",
                description = "Shades of Blue is a remix album by American hip hop musician Madlib over the archives of Blue Note Records. It was released by Blue Note Records on June 24, 2003.\nSam Samuelson of AllMusic gave the album 4 out of 5 stars, saying: \"Intent listening doesn\'t really give much up, but for smooth subconscious grooves, it\'s perfect.\nIn 2014, Paste placed it at number 11 on the \"12 Classic Hip-Hop Albums That Deserve More Attention\" list.",
                imgId = R.drawable.madlib_shades_front,
                sharedCount = sharedCount,
                updateSharedCount = updateShared
            )
        }
    }
}

@Preview
@Composable
fun ProductPreview() {
    Product(
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

@Composable
fun Product(
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