
# RevealSwipeLazyColumn

A component that allows you to display hidden swipe actions (left or right) for each item inside a `LazyColumn`.

![example](https://github.com/user-attachments/assets/5277dab2-0dfb-4b99-983d-569d3cdd4a9a)


## Features
- Swipe left or right to reveal custom actions.
- Fully composable.
- Easy to integrate into existing Compose projects.


---

## Installation

### 1. Add JitPack to your **settings.gradle** (project-level):
```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

``` 
### 2. Add the library dependency to your **build.gradle **:

Add the following dependency:

```gradle
dependencies {
    implementation("com.github.ucielLL:RevealSwipeLazyColum:1.0.0")
}
```

## How use it 
### Create a data class that implements ItemsModel<T>, where T is the ID type of your items
```
data class UserItem(
    override val id: Int,
    val name: String
) : ItemsModel<Int>
```
### Use SwipeLazyColumn() in your Composable:
```
val users = remember {
    listOf(
        UserItem(1, "Alice"),
        UserItem(2, "Bob"),
        UserItem(3, "Charlie")
    )
}

SwipeLazyColumn(
    modifiercontaner = Modifier.padding(start = 5.dp, end = 5.dp),
    shape = RoundedCornerShape(20.dp),
    space = 8.dp,
    items = users,
    leftActions = { item, close ->
        // Your left swipe composable here
    },
    rightActions = { item, close ->
        // Your right swipe composable here
    }
) { item ->
    // Your main item content here
}

``` 
### You can use ActionIcon() inside leftActions { item, close -> } or rightActions { item, close -> }:
```
 leftActions = { item, close->
            ActionIcon(
                onClick = {  close()},
                backgroundColor = Color.Red,
                icon = Icons.Default.Email,
                modifier = Modifier.fillMaxHeight().padding( 2.dp),
                contentDescription = "Icon"
            )
        }

```
f you only want to use the swipe animation, use RevealSwipe(). 
