import React from "react";
import { Card, CardContent, InputAdornment, OutlinedInput, Link } from "@mui/material";
import { Avatar } from '@mui/material';

function Comment(props) {
    console.log(props);
  const { text, userId, username } = props;
  return (
    <CardContent sx={{ padding: 2, bgcolor: '#f7f7f7' }}>
      <OutlinedInput
        disabled
        id='outlined-adornment-amount'
        multiline
        inputProps={{ maxLength: 25 }}
        fullWidth
        value={text}
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
        sx={{
          bgcolor: 'transparent',
          border: '1px solid #ccc',
          borderRadius: 4,
          padding: 1,
          '&:hover': {
            border: '1px solid #aaa',
          },
        }}
      ></OutlinedInput>
    </CardContent>
  );
}

export default Comment;