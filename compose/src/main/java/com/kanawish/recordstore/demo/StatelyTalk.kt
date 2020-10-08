package com.kanawish.recordstore.demo

import com.kanawish.recordstore.state.Product
import com.kanawish.recordstore.state.ProductEditorState

/**
 * A bunch of pre-defined demo constants and data set
 * for the stately and composed talk, oct. 2020
 */

val statelyProducts = listOf(
    Product(
        name = "Pocket Watch",
        description = "A pocket watch (or pocketwatch) is a watch that is made to be carried in a pocket, as opposed to a wristwatch, which is strapped to the wrist. They were the most common type of watch from their development in the 16th century until wristwatches became popular after World War I during which a transitional design, trench watches, were used by the military.",
        imageUrl = "file:///android_asset/stately/jonathan-pielmayer-eMzR8FW4N9M-unsplash.jpg",
        price = 19999
    ),
    Product(
        name = "Belle Isle Estate",
        description = "Belle Isle Castle is a historic Irish landmark situated on Belle Island. The estate stretches over 470-acres across Lisbellaw, County Fermanagh, Northern Ireland.",
        imageUrl = "file:///android_asset/stately/k-mitch-hodge-plIpwsFLlmc-unsplash.jpg",
        price = 499999999
    ),
    Product(
        name = "Aston Martin DB5",
        description = "The Aston Martin DB5 is a British luxury grand tourer (GT) that was made by Aston Martin and designed by the Italian coachbuilder Carrozzeria Touring Superleggera. Released in 1963, it was an evolution of the final series of DB4.",
        imageUrl = "file:///android_asset/stately/aleks-marinkovic-xcQzwkYZz1I-unsplash.jpg",
        price = 79999999
    ),
)
