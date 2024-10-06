import React, { useState } from "react";
import { Card, CardContent, InputAdornment, OutlinedInput, Link, Button } from "@mui/material";
import { Avatar } from '@mui/material';
import { PostAdd } from '@mui/icons-material';

function CommentForm(props) {
  console.log(props);
  const {postId, userId, username } = props;
  const [text, setText] = useState("");

  const handleChange = (value) => {
    setText(value);
  }

  const saveComment = () =>{
    fetch('/comments', {
        method: "POST",
        headers: {
          'Content-Type' : 'application/json',
          'Authorization' : localStorage.getItem("tokenKey"),
        },
        body: JSON.stringify({
            userId: userId,
            text: text,
            postId: postId,
    }),
    })
    .then((res) => res.json())
    .catch((err) => console.log(err))
}

  const handleSubmit = () => {
    saveComment();
    setText("");
  }
  return (
    <CardContent sx={{ padding: 2, bgcolor: '#f7f7f7' }}>
      <OutlinedInput
        id='outlined-adornment-amount'
        multiline
        inputProps={{ maxLength: 250 }}
        fullWidth
        onChange={(i) => handleChange(i.target.value)}
        startAdornment={
          <InputAdornment position="start">
            <Link href={`/users/${userId}`} underline="none">
              <Avatar sx={{
                width: 30,
                height: 30,
                marginRight: 2,
                border: '1px solid #ddd',
                bgcolor: '#fff',
                color: '#333'
              }} />
            </Link>
          </InputAdornment>
        }
        endAdornment={
          <InputAdornment position="end">
            <Button
            onClick={handleSubmit}
              variant="contained"
              size="small"
              sx={{
                bgcolor: '#FF3737', // red color
                color: '#fff',
                '&:hover': {
                  bgcolor: '#FF6969', // darker red on hover
                },
              }}
            >
              <PostAdd fontSize="small" /> {/* post icon */}
            </Button>
          </InputAdornment>
        }
        sx={{
          bgcolor: 'transparent',
          border: '1px solid #ccc',
          borderRadius: 4,
          padding: 1,
          '&:hover': {
            border: '1px solid #aaa',
          },
        }}
        value={text}
      ></OutlinedInput>
    </CardContent>
  );
}

export default CommentForm;