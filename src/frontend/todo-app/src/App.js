import React from 'react';
import './App.css';
import { connect } from 'react-redux';
import { Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import SignIn from './components/SignIn';
import SignUp from './components/SignUp';
import 'bootstrap/dist/css/bootstrap.min.css';
import UserPage from "./pages/UserPage";
import TodoItemPage from "./pages/TodoItemPage";

class App extends React.Component {
    render() {
        return (
            <div>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/home" element={<Home />} />
                    <Route path="/signin" element={<SignIn />} />
                    <Route path="/signup" element={<SignUp />} />
                    <Route path="/userpage" element={<UserPage />} />
                    <Route path="/todo-list/:listId/items" element={<TodoItemPage />} />

                </Routes>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        isLoggedIn: state.isLoggedIn,
        userFirstName: state.userFirstName,
    };
};

export default connect(mapStateToProps)(App);
