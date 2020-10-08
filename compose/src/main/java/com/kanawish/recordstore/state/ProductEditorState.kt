package com.kanawish.recordstore.state


/**
 * Example 3 - Product Editor
 */
sealed class ProductEditorState {
}

/*
    UML Diagram we're aiming for:

    @startuml
    [*] --> CLOSED
    CLOSED --> EDITING : edit()

    EDITING : product
    EDITING --> EDITING : edit()

    EDITING -down-> SAVING : save()
    EDITING -down-> DELETING : delete()
    EDITING -left-> CLOSED : cancel()

    DELETING -up-> CLOSED : deleted()
    DELETING --> ERROR : error(msg)
    DELETING : product

    SAVING -up-> CLOSED : saved()
    SAVING --> ERROR : error(msg)
    SAVING : product

    ERROR--> EDITING: edit()
    ERROR : product
    @enduml
 */
