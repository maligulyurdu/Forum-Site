import React, { useState, useRef, useEffect } from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { red, grey } from '@mui/material/colors';
import CommentIcon from '@mui/icons-material/Comment'; 
import CollapseIcon from '@mui/icons-material/KeyboardArrowUp';
import Box from '@mui/material/Box';
import FavoriteIcon from '@mui/icons-material/Favorite'; 
import Avatar from '@mui/material/Avatar'; 
import Link from '@mui/material/Link'; 
import Comment from '../Comment/Comment';
import CommentForm from '../Comment/CommentForm'

function Post(props) {
  const { title, text, username, userId, postId, likes } = props;
  const [expanded, setExpanded] = React.useState(false);
  const [commentList, setCommentList] = useState([]);
  const isInitialMount = useRef(true);
  const [likeCount, setLikeCount] = useState(likes.length);
  const [isLiked, setIsLiked] = useState(false);
  const [likeId, setLikeId] = useState(null);
  let disabled = localStorage.getItem("currentUser") == null ? true:false;

  const checkLikes = () => {
    var likeControl = likes.find((like => "" + like.userId === localStorage.getItem("currentUser")));
    if(likeControl!= null){
      setLikeId(likeControl.id);
      setIsLiked(true);}
  }

  const handleExpandClick = () => {
    setExpanded(!expanded);
    refreshComments();
    console.log(commentList);
  };

  const handleLikeClick = () => {
    setIsLiked(!isLiked);
    if(!isLiked){
      saveLike();
      setLikeCount(likeCount + 1)
    }
    else{
      deleteLike();
      setLikeCount(likeCount - 1)
    }
  };

  const handleAvatarClick = () => {
    console.log(`Avatar clicked for user ${username} with ID ${userId}`);
  };

  const saveLike = () => {
      fetch('/likes',{
        method: "POST",
        headers: {
          'Content-Type' : 'application/json',
          'Authorization' : localStorage.getItem("tokenKey"),
        },
        body: JSON.stringify({
          postId: postId,
          userId: localStorage.getItem("currentUser"),
        }),
    })
    .then(response => response.json())
    .catch(err => console.log(err))
  }

  const deleteLike = () => {
    fetch('/likes/' + likeId,{
      method: "DELETE",
      headers: {
        'Authorization' : localStorage.getItem("tokenKey"),
      }
    })
    .then(response => response.json())
    .catch(err => console.log(err))
  }

  const refreshComments = () => {
    fetch('/comments?postId=' + postId)
      .then(response => response.json())
      .then(data => setCommentList(data));
  }

  useEffect(() => {
    if (isInitialMount.current) {
      isInitialMount.current = false;
    } else {
      refreshComments();
    }
  }, [commentList]);

  useEffect(() => {checkLikes()}, [])

  return (
    <div className="postContainer">
      <Card sx={{
        maxWidth: 600, // Increased card width
        backgroundColor: grey[900], 
        color: grey[50], 
        marginBottom: 20, 
        borderRadius: 10, // Added border radius
        boxShadow: '0px 0px 10px rgba(0,0,0,0.2)', // Added box shadow
      }}>
        
        <Box sx={{ display: 'flex', alignItems: 'center', padding: 2 }}>
          <Link href={`/users/${userId}`} underline="none" onClick={handleAvatarClick}> 
            <Avatar sx={{ width: 50, height: 50, marginRight: 2, border: '1px solid #ddd' }} /> 
          </Link>
          <Typography variant="body2" color={grey[400]}>
            {username}
          </Typography>
        </Box>
        
        <CardContent sx={{ padding: 3 }}>
          <Typography gutterBottom variant="h5" component="div" sx={{ color: red[700], fontSize: 24 }}>
            {title}
          </Typography>
          <Typography variant="body2" color={grey[400]}>
            {text}
          </Typography>
        </CardContent>
        <CardActions sx={{ padding: 2, justifyContent: 'space-between' }}>
          <Button disabled
          size="small" sx={{ color: red[700], marginLeft: 0 }} onClick={handleLikeClick}>
            {isLiked ? <FavoriteIcon sx={{ color: red[700] }} /> : <FavoriteIcon sx={{ color: grey[400] }} />}
            <Typography variant="body2" sx={{ fontSize: 18, fontWeight: 600, marginLeft: 5 }}>
              {likeCount}
            </Typography>
          </Button>
          <Box sx={{ flexGrow: 1, marginLeft: 10 }}></Box>
          <Button size="small" sx={{ color: red[700] }} onClick={handleExpandClick}>
            {expanded ? <CollapseIcon /> : <CommentIcon />}
          </Button>
        </CardActions>
        {expanded && (
          <CardContent sx={{ padding: 3 }}>
            {disabled? "":
            <CommentForm userId={localStorage.getItem("currentUser")} username={localStorage.getItem("username")}
             postId={postId}></CommentForm>}
            {commentList.map((comment) => (
              <Comment id={comment.id} text={comment.text} userId={2} username={"try user"}/>
            ))}
          </CardContent>
        )}
      </Card>
    </div>
  );
}

export default Post;