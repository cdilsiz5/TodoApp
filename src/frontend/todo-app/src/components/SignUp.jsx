import React, { useState } from "react";
import { Form, Button, Alert, Container, Row, Col, Modal } from "react-bootstrap";
import { signup } from "../api/ApiCalls";
import NavigationBar from "./Navbar";
import { useNavigate } from "react-router-dom";


const SignUp = () => {
    const [form, setForm] = useState({
        name: "",
        surname: "",
        email: "",
        password: "",
        passwordRepeat: "",
        userRole: "ROLE_USER",
    });
    const navigate = useNavigate();
    const [errors, setErrors] = useState({});
    const [success, setSuccess] = useState(false);
    const [showModal, setShowModal] = useState(false); // State to control modal visibility

    const onChange = (event) => {
        const { name, value } = event.target;
        setSuccess(false);
        setErrors((previousErrors) => ({
            ...previousErrors,
            [name]: undefined,
        }));
        setForm((previousForm) => ({
            ...previousForm,
            [name]: value,
        }));
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        const { name, surname, email, password, userRole } = form;
        const userData = {
            name,
            surname,
            email,
            password,
            userRole,
        };

        try {
            const response = await signup(userData);
            if (response.status === 201 || response.status === 200) {
                setSuccess(true);
                setShowModal(true);
                setTimeout(() => {
                    navigate("/signin");
                }, 1500);

            }
        } catch (err) {
            if (err.response && err.response.data && err.response.data.validationErrors) {
                setErrors(err.response.data.validationErrors);
            } else {
                console.error("An error occurred:", err);
                setErrors({ general: "An error occurred. Please try again later." });
            }
        }
    };


    const { name: nameError, surname: surnameError, email: emailError, password: passwordError } = errors;

    let passwordRepeatError;
    if (form.password !== form.passwordRepeat) {
        passwordRepeatError = "Password Mismatch";
    }

    return (
        <div>
            <NavigationBar />

            <Container className="d-flex justify-content-center mt-5">
                <Row className="w-100 justify-content-center">
                    <Col xs={12} md={8} lg={6}>
                        <h2 className="text-center mb-4">Sign Up</h2>
                        {errors.general && <Alert variant="danger">{errors.general}</Alert>}
                        {success && <Alert variant="success">User successfully registered!</Alert>}
                        <Form onSubmit={handleSubmit}>
                            <Form.Group controlId="formName" className="mb-3">
                                <Form.Label>First Name</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="name"
                                    placeholder="Enter first name"
                                    value={form.name}
                                    isInvalid={!!nameError}
                                    onChange={onChange}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {nameError}
                                </Form.Control.Feedback>
                            </Form.Group>

                            <Form.Group controlId="formSurname" className="mb-3">
                                <Form.Label>Last Name</Form.Label>
                                <Form.Control
                                    type="text"
                                    name="surname"
                                    placeholder="Enter last name"
                                    value={form.surname}
                                    isInvalid={!!surnameError}
                                    onChange={onChange}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {surnameError}
                                </Form.Control.Feedback>
                            </Form.Group>

                            <Form.Group controlId="formEmail" className="mb-3">
                                <Form.Label>Email</Form.Label>
                                <Form.Control
                                    type="email"
                                    name="email"
                                    placeholder="Enter email"
                                    value={form.email}
                                    isInvalid={!!emailError}
                                    onChange={onChange}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {emailError}
                                </Form.Control.Feedback>
                            </Form.Group>

                            <Form.Group controlId="formPassword" className="mb-3">
                                <Form.Label>Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    name="password"
                                    placeholder="Password"
                                    value={form.password}
                                    isInvalid={!!passwordError}
                                    onChange={onChange}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {passwordError}
                                </Form.Control.Feedback>
                            </Form.Group>

                            <Form.Group controlId="formPasswordRepeat" className="mb-3">
                                <Form.Label>Repeat Password</Form.Label>
                                <Form.Control
                                    type="password"
                                    name="passwordRepeat"
                                    placeholder="Repeat Password"
                                    value={form.passwordRepeat}
                                    isInvalid={!!passwordRepeatError}
                                    onChange={onChange}
                                />
                                <Form.Control.Feedback type="invalid">
                                    {passwordRepeatError}
                                </Form.Control.Feedback>
                            </Form.Group>

                            <Button
                                variant="primary"
                                type="submit"
                                className="w-100"
                                disabled={passwordRepeatError !== undefined}
                            >
                                Sign Up
                            </Button>
                        </Form>
                    </Col>
                </Row>
            </Container>

             <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Registration Successful</Modal.Title>
                </Modal.Header>
                <Modal.Body>User successfully created!</Modal.Body>
                <Modal.Footer>
                    <Button variant="primary" onClick={() => setShowModal(false)}>
                        Close
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default SignUp;
