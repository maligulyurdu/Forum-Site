import React, { useState } from 'react';
import Card from '@mui/material/Card';
import CardActions from '@mui/material/CardActions';
import CardContent from '@mui/material/CardContent';
import Button from '@mui/material/Button';
import Typography from '@mui/material/Typography';
import { red, grey } from '@mui/material/colors';
import Box from '@mui/material/Box';
import Avatar from '@mui/material/Avatar'; 
import Link from '@mui/material/Link';
import SendIcon from '@mui/icons-material/Send'; 
import OutlinedInput from '@mui/material/OutlinedInput';
import CircularProgress from '@mui/material/CircularProgress'; // Import CircularProgress

function PostForm(props) {
  const {username, userId, refreshPosts } = props;
  const [expanded, setExpanded] = React.useState(false); 
  const [text, setText] = useState("");
  const [title, setTitle] = useState("");
  const [isSent, setIsSent] = useState(false);
  const [loading, setLoading] = useState(false); // State to manage loading

  const savePost = () => {
    return fetch("/posts",
      {
        method: "POST",
        headers: {
          'Content-Type' : 'application/json',
          'Authorization' : localStorage.getItem("tokenKey"),
        },
        body: JSON.stringify({
            title: title,
            text: text,
            userId: userId,
        }),
      }
    ).then((res) => res.json())
    .catch((err) => console.log("error"))
  }

  const handleSubmit = async () => {
    setLoading(true); // Show loading icon
    await savePost();
  
    // Add a 2-second delay
    setTimeout(() => {
      setLoading(false); // Hide loading icon
      setIsSent(true);
      setTitle("");
      setText("");
      refreshPosts();
    }, 2000);
  };
  

  const handleTitle = (value) => {
    setTitle(value);
    setIsSent(false);
  };

  const handleText = (value) =>{
    setText(value);
    setIsSent(false);
  }

  const handleAvatarClick = () => {
    console.log(`Avatar clicked for user ${username} with ID ${userId}`);
  };

  return (
    <div className="postContainer" style={{ position: 'relative' }}>
      {loading && (
  <div style={{
    position: 'fixed',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
    zIndex: 1000
  }}>
    <CircularProgress 
      sx={{ color: grey[400] }}  // Set color to gray
      thickness={6}              // Increase the thickness
      size={60}                  // Optional: Adjust the size if needed
    />
  </div>
)}
      <Card sx={{
        maxWidth: 600, 
        backgroundColor: grey[900], 
        color: grey[50], 
        marginBottom: 20, 
        borderRadius: 10, 
        boxShadow: '0px 0px 10px rgba(0,0,0,0.2)', 
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
            <OutlinedInput
              id='outlined-adornment-amount'
              multiline
              placeholder='Title'
              inputProps={{maxLength : 25}}
              fullWidth
              value={title}
              onChange={(i) => handleTitle(i.target.value)}
              sx={{
                backgroundColor: grey[800],
                color: grey[50],
                border: '1px solid #ddd',
                borderRadius: 5,
                padding: 2,
                fontSize: 24, // added this line
              }}
            />
          </Typography>
          <Typography variant="body2" color={grey[400]}>
            <OutlinedInput
              id='outlined-adornment-amount'
              multiline
              placeholder='Text'
              inputProps={{maxLength : 100}}
              fullWidth
              value={text}
              onChange={(i) => handleText(i.target.value)}
              sx={{
                backgroundColor: grey[800],
                color: grey[50],
                border: '1px solid #ddd',
                borderRadius: 5,
                padding: 2,
                fontSize: 16, // added this line
              }}
            />
          </Typography>
        </CardContent>
        <CardActions sx={{ padding: 2, justifyContent: 'space-between' }}>
          <Box sx={{ flexGrow: 1, textAlign: 'right' }}>
            <Button size="small" 
            sx={{ color: red[700] }}
            onClick={handleSubmit}>
              <SendIcon />
            </Button>
          </Box>
        </CardActions>
        {expanded && (
          <CardContent sx={{ padding: 3 }}>
            <Typography variant="body2" color={grey[400]}>
              {username}
            </Typography>
          </CardContent>
        )}
      </Card>
    </div>
  );
}

export default PostForm;
