import instance from './Authentication.jsx';
import axios from 'axios';
import { API_TODO_URL } from '../Config.js';

// User Signup API
export const signup = (body) => {
   return axios.post(`${API_TODO_URL}users/signup`, body);
};

// User Update API
export const updateUser = (userId, body) => {
   return instance.put(`${API_TODO_URL}users/${userId}`, body);
};

// Fetch User Details API
export const fetchUserDetails = () => {
   return instance.get(`${API_TODO_URL}users`);
};

// User Login API
export const signin = (body) => {
   return axios.post(`${API_TODO_URL}auth/signin`, body);
};

// Refresh Token API
export const refreshToken = () => {
   return axios.post(`${API_TODO_URL}auth/refreshToken`);
};

// Logout API
export const logout = (userId) => {
   return axios.post(`${API_TODO_URL}auth/logout/${userId}`);
};

// Validate Token API
export const validateToken = () => {
   return axios.get(`${API_TODO_URL}auth/validate`);
};

// Get TodoList by ID API
export const getTodoListById = (id) => {
   return instance.get(`${API_TODO_URL}todolists/${id}`);
};

// Update TodoList by ID API
export const updateTodoList = (id, body) => {
   return instance.put(`${API_TODO_URL}todolists/${id}`, body);
};

// Delete TodoList by ID API
export const deleteTodoList = (id) => {
   return instance.delete(`${API_TODO_URL}todolists/${id}`);
};

// Create TodoList API
export const createTodoList = (userId, body) => {
   return instance.post(`${API_TODO_URL}todolists/create?userId=${userId}`, body);
};
// Get All TodoLists by User ID API
export const getTodoListsByUserId = (userId) => {
   return instance.get(`${API_TODO_URL}todolists/user/${userId}`);
};

// Get TodoItem by ID API
export const getTodoItemById = (todoId) => {
   return instance.get(`${API_TODO_URL}todoitems/${todoId}`);
};

// Update TodoItem by ID API
export const updateTodoItem = (todoId, body) => {
   return instance.put(`${API_TODO_URL}todoitems/${todoId}`, body);
};

// Delete TodoItem by ID API
export const deleteTodoItem = (todoId) => {
   return instance.delete(`${API_TODO_URL}todoitems/${todoId}`);
};

// Create TodoItem API
export const createTodoItem = (listId, body) => {
   return instance.post(`${API_TODO_URL}todoitems/create/${listId}`, body);
};


// Get All TodoItems by List ID API
export const getTodoItemsByListId = (listId) => {
   return instance.get(`${API_TODO_URL}todoitems/list/${listId}`);
};

   