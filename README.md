# GitHub-public-repos-list
This project will show list of public repostories in github. User can add comment for each repo list.
<br><br>
<h2>Libraries used </h2>
<b>Room Database</b> 
<br><p>To store user comments on repository. Rom DB provide nice layer over SQLiteDB which reduces the boidler plate code and check compile time errors.</p>
<br>
   <p>implementation 'anroid.arch.persistence.room:runtime:1.0.0'</p>
   <p>annotationProcessor 'anroid.arch.persistence.room:compiler:1.0.0'</p>
<b>Retrofit2</b> 
<br><p>This library is used for network call to get all public repositories. Retrofit will convert the response body into a model or entity class. </p>
<br>
   <p>implementation 'com.squareup.retrofit2:retrofit:2.7.2'</p>
    <p>implementation 'com.squareup.retrofit2:converter-gson:2.7.2'</p> 
    
<b>Glide</b> 
<br><p>This is used for showing images from URLs. It will also handle bitmap caching. </p>
<br>
    <p>implementation 'com.github.bumptech.glide:glide:4.11.0'</p>
    <p>annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'</p>
    
<b>Circle ImageView</b> 
<br><p>To show a rounded Image. </p>
<br>
    <p>    implementation 'de.hdodenhof:circleimageview:3.1.0'</p>
