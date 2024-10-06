import React, { useState, useEffect } from "react";
import Post from '../Post/Post'
import PostForm from "../Post/PostForm";

function Home(){
    const [posts, setPosts] = useState([]);

    const refreshPosts = () =>{
        fetch('/posts')
            .then(response => response.json())
            .then(data => setPosts(data));

    }
    

    useEffect(() => {
        refreshPosts()
    }, [posts]);

    return (
        <div className="container">
            <h1>HOME</h1>
            {localStorage.getItem("currentUser") === null ? "":
            <PostForm userId = {localStorage.getItem("currentUser")}
             username = {localStorage.getItem("username")} refreshPosts = {refreshPosts}></PostForm>}
            
            <h1>Posts</h1>
            <ul>
            
                {posts.map(post => (
                   <Post likes = {post.postLikes} postId = {post.id} userId={post.userId} username={post.username}
                    text = {post.text} title = {post.title}> </Post>
                ))}
            </ul>
        </div>
    );
}

export default Home;