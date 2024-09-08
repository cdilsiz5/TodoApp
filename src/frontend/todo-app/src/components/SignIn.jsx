import React, { useState } from "react";
import { Form, Button } from "react-bootstrap";
import { loginHandler } from "../redux/AuthActions";
import { connect } from "react-redux";
import { useNavigate } from "react-router-dom";
import NavigationBar from "./Navbar";
import Input from "./Input";

const SignIn = (props) => {
    const [userEmail, setUserEmail] = useState("");
    const [userPassword, setUserPassword] = useState("");
    const [error, setError] = useState("");
    const [fieldErrors, setFieldErrors] = useState({});
    const navigate = useNavigate();

    const handleSubmit = (e) => {
        e.preventDefault();

        const credentials = {
            userEmail,
            userPassword,
        };

        props.loginHandler(credentials)
            .then((response) => {
                setError("");
                setFieldErrors({});
                navigate("/userpage");
            })
            .catch((err) => {
                if (err.response && err.response.data && err.response.data.errors) {
                    const apiFieldErrors = {};
                    err.response.data.errors.forEach((error) => {
                        apiFieldErrors[error.field] = error.defaultMessage;
                    });
                    setFieldErrors(apiFieldErrors);
                } else {
                    setError(err.response ? err.response.data.message : "An error occurred");
                }
            });
    };

    return (
        <div>
            <NavigationBar />

            <div className="d-flex justify-content-center align-items-center" style={{ minHeight: '100vh' }}>
                <div className="col-md-6 col-lg-4">
                    <div className="card p-4">
                        <h2 className="text-center">Sign In</h2>
                        {error && <div className="alert alert-danger">{error}</div>}
                        <Form onSubmit={handleSubmit}>
                            <Input
                                label="Email"
                                type="email"
                                name="userEmail"
                                value={userEmail}
                                onChange={(e) => setUserEmail(e.target.value)}
                                error={fieldErrors.userEmail} // Display field-specific error
                            />

                            <Input
                                label="Password"
                                type="password"
                                name="userPassword"
                                value={userPassword}
                                onChange={(e) => setUserPassword(e.target.value)}
                                error={fieldErrors.userPassword}
                            />

                            <Button variant="primary" type="submit" className="w-100 mt-3">
                                Sign In
                            </Button>
                        </Form>
                    </div>
                </div>
            </div>
        </div>
    );
};

const mapDispatchToProps = (dispatch) => {
    return {
        loginHandler: (credentials) => dispatch(loginHandler(credentials)), // Dispatch login action
    };
};

export default connect(null, mapDispatchToProps)(SignIn);
