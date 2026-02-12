/*
import { createSlice } from '@reduxjs/toolkit';

const authSlice = createSlice({
  name: 'auth',
  initialState: {
    user: JSON.parse(localStorage.getItem('user')) || null,
    token: localStorage.getItem('token') || null,
    userId: localStorage.getItem('userId') || null
  },
  reducers: {
    setCredentials: (state, action) => {
      state.user = action.payload.user;
      state.token = action.payload.token;
      state.userId = action.payload.user.sub;

      localStorage.setItem('token', action.payload.token);
      localStorage.setItem('user', JSON.stringify(action.payload.user));
      localStorage.setItem('userId', action.payload.user.sub);
    },
    
    logout: (state) => {
      state.user = null;
      state.token = null;
      state.userId = null;

      localStorage.removeItem('token');
      localStorage.removeItem('user');
      localStorage.removeItem('userId');
    }
  }
});

export const { setCredentials, logout } = authSlice.actions;
export default authSlice.reducer;
*/

import { createSlice } from "@reduxjs/toolkit";

const authSlice = createSlice({
  name: "auth",
  initialState: {
    user: JSON.parse(localStorage.getItem("user")) || null,
    userId: localStorage.getItem("userId") || null,
    isAuthenticated: !!localStorage.getItem("user"),
  },
  reducers: {
    setUser: (state, action) => {
      state.user = action.payload;
      state.userId = action.payload.sub;
      state.isAuthenticated = true;

      localStorage.setItem("user", JSON.stringify(action.payload));
      localStorage.setItem("userId", action.payload.sub);
    },

    logout: (state) => {
      state.user = null;
      state.userId = null;
      state.isAuthenticated = false;

      localStorage.removeItem("user");
      localStorage.removeItem("userId");
    },
  },
});

export const { setUser, logout } = authSlice.actions;
export default authSlice.reducer;

