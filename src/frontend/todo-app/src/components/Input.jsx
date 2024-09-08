import React from 'react';
import { Form } from 'react-bootstrap';

const Input = (props) => {
    const { label, error, onChange, name, type, value } = props;

    return (
        <Form.Group className="mb-3">
            {label && <Form.Label>{label}</Form.Label>}
            <Form.Control
                type={type}
                value={value}
                onChange={onChange}
                name={name}
                isInvalid={!!error}
            />
            {error && <Form.Control.Feedback type="invalid">{error}</Form.Control.Feedback>}
        </Form.Group>
    );
};

export default Input;
