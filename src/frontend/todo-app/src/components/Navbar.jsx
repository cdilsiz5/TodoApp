import React from 'react';
import { Navbar, Nav, Button } from 'react-bootstrap';
import { connect } from 'react-redux';
import { logoutSuccess } from '../redux/AuthActions';
import { logout } from '../api/ApiCalls';
import { useNavigate } from "react-router-dom"; // Import useNavigate

const NavigationBar = ({ name, userId, surname, mail, isLoggedIn, onLogoutSuccess }) => {
    const navigate = useNavigate();

    const handleLogout = async () => {
        try {
            console.log("User ID:", userId);
            await logout(userId);
            onLogoutSuccess();
            navigate("/home");
        } catch (error) {
            console.error('Logout error', error);
        }
    };

    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Navbar.Brand href="/">My Todo App</Navbar.Brand>
            <Navbar.Toggle aria-controls="basic-navbar-nav" />
            <Navbar.Collapse id="basic-navbar-nav">
                <Nav className="ml-auto">
                    {!isLoggedIn ? (
                        <>
                            <Nav.Link href="/signin">Sign In</Nav.Link>
                            <Nav.Link href="/signup">Sign Up</Nav.Link>
                        </>
                    ) : (
                        <>
                            <Nav.Item href="/userpage">
                                {`${name} ${surname} (${mail})`}
                            </Nav.Item>
                            <Button variant="outline-light" onClick={handleLogout} className="ml-2">
                                Logout
                            </Button>
                        </>
                    )}
                </Nav>
            </Navbar.Collapse>
        </Navbar>
    );
};

const mapStateToProps = (state) => {
    return {
        name: state.name,
        surname: state.surname,
        mail: state.email,
        userId: state.id,
        isLoggedIn: state.isLoggedIn,
    };
};

const mapDispatchToProps = (dispatch) => {
    return {
        onLogoutSuccess: () => dispatch(logoutSuccess()),
    };
};

export default connect(mapStateToProps, mapDispatchToProps)(NavigationBar);
