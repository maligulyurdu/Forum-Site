import React from "react";
import { Link } from "react-router-dom";
import AppBar from "@mui/material/AppBar";
import Toolbar from "@mui/material/Toolbar";
import Typography from "@mui/material/Typography";
import Button from "@mui/material/Button";
import { red, grey } from "@mui/material/colors";
import LockOpenIcon from "@mui/icons-material/LockOpen";

function Navbar() {
  const userId = localStorage.getItem("currentUser");

  const onClick = () => {
    localStorage.removeItem("tokenKey")
    localStorage.removeItem("currentUser")
    localStorage.removeItem("username")
    window.location.href = "/auth";
  }

  return (
    <AppBar position="static" sx={{ backgroundColor: grey[900] }}>
      <Toolbar>
        <Button color="inherit" component={Link} to="/" sx={{ marginRight: 2 }}>
          Home
        </Button>
        <Typography variant="h6" component="div" sx={{ flexGrow: 1, textAlign: "center", color: grey[50] }}>
          {/* your logo or title here */}
        </Typography>
        {userId?.length > 0 ? (
          <>
            <Button color="inherit" sx={{ marginRight: 2 }}
            onClick={onClick}>
              <LockOpenIcon />
            </Button>
            <Button color="inherit" component={Link} to={`/users/${userId}`} sx={{ color: red[700] }}>
              {localStorage.getItem("username")}
            </Button>
          </>
        ) : (
          <Button color="inherit" component={Link} to="/auth" sx={{ marginRight: 2, color: red[700] }}>
            Login/Register
          </Button>
        )}
      </Toolbar>
    </AppBar>
  );
}

export default Navbar;