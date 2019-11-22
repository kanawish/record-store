# Curriculum / DI

## Some key Architecture Component quotes

https://developer.android.com/topic/libraries/architecture/viewmodel#further-information

>As your data grows more complex, you might choose to have a separate class just to load the data. The purpose of ViewModel is to encapsulate the data for a UI controller to let the data survive configuration changes. For information about how to load, persist, and manage data across configuration changes, see Saving UI States.

>[The Guide to Android App Architecture](https://developer.android.com/jetpack/docs/guide#overview) suggests building a repository class to handle these functions.

![high level architecture diagram](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

*Note: One constant source of confusion for folks less familiar with MVVM is they equate "M" with "Repository". Repositories in sample code are mostly about saving/loading. Keep an eye out for 3rd party apps that have "Models" with business logic, to provide as examples.*

[Jose's Article here](https://medium.com/androiddevelopers/viewmodels-and-livedata-patterns-antipatterns-21efaef74a54) gives some hints. But there's older guidance on LiveData usage, and only some hints at what should be Model/Repo responsability vs VM. [Yiggit's and Sean's talks are a bit better at explaining this, go look at those next]


## Some "WiP" notes

Want to do graphQL calls, so the following is relevant:

- https://community.shopify.com/c/Shopify-APIs-SDKs/Possible-to-import-Shopify-s-GraphQL-Schema-SDL-into-Postman/td-p/567522
- [Insomnia REST/GraphQL client](https://insomnia.rest/)
- [The introspection GraphQL query](https://gist.github.com/craigbeck/b90915d49fda19d5b2b17ead14dcd6da)


