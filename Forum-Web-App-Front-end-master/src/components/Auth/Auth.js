import { Grid, FormControl, InputLabel, Input, Button, Typography } from "@mui/material";
import React from "react";
import { useNavigate } from 'react-router-dom';

function Auth() {
  const [username, setUsername] = React.useState("");
  const [password, setPassword] = React.useState("");

  const handleUsername = (event) => {
    setUsername(event.target.value);
  };

  const handlePassword = (event) => {
    setPassword(event.target.value);
  };

  const sendRequest = (path) => {
    fetch("/auth/" + path, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username: username, password: password,}),
    })
      .then((res) => res.json())
      .then((result) => {
        localStorage.setItem("tokenKey", result.message);
        localStorage.setItem("currentUser", result.userId);
        localStorage.setItem("username", username);
        return result;
      })
      .catch((err) => {
        console.log(err);
        return { error: "Request failed" }; // Define the result variable and return it
      });
  };

  const handleButton = (path) => {
    sendRequest(path);
    setUsername("");
    setPassword("");
    window.location.href = "/auth";
  };


  return (
    <Grid container spacing={2} sx={{ width: "300px", margin: "40px auto" }}>
      <Grid item xs={12}>
        <FormControl>
          <InputLabel sx={{ color: "red" }} htmlFor="username">
            Username
          </InputLabel>
          <Input
            sx={{ backgroundColor: "black", color: "white" }}
            id="username"
            onChange={handleUsername}
          />
        </FormControl>
      </Grid>
      <Grid item xs={12}>
        <FormControl>
          <InputLabel sx={{ color: "red" }} htmlFor="password">
            Password
          </InputLabel>
          <Input
            sx={{ backgroundColor: "black", color: "white" }}
            id="password"
            type="password"
            onChange={handlePassword}
          />
        </FormControl>
      </Grid>
      <Grid item xs={12}>
        <Button
          sx={{ marginTop: "20px", backgroundColor: "red", color: "white" }}
          onClick={() => handleButton("register")}
        >
          Register
        </Button>
      </Grid>
      <Grid item xs={12}>
        <Typography
          sx={{ fontSize: "12px", color: "gray", marginTop: "10px" }}
        >
          Already registered?
        </Typography>
      </Grid>
      <Grid item xs={12}>
        <Button
          sx={{ marginTop: "10px", backgroundColor: "black", color: "white" }}
          onClick={() => handleButton("login")}
        >
          Login
        </Button>
      </Grid>
    </Grid>
  );
}

export default Auth;