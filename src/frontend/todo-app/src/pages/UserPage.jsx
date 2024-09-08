import React, { useEffect, useState } from 'react';
import { getTodoListsByUserId, createTodoList } from '../api/ApiCalls';
import { connect } from 'react-redux';
import NavigationBar from "../components/Navbar";
import { Button, Form, Modal, Table } from 'react-bootstrap';

const UserPage = ({ userId }) => {
    const [todoLists, setTodoLists] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [newTodoList, setNewTodoList] = useState({
        title: '',
        description: ''
    });

    const handleChange = (event) => {
        const { name, value } = event.target;
        setNewTodoList((prevList) => ({
            ...prevList,
            [name]: value,
        }));
    };

    useEffect(() => {
        const fetchTodoLists = async () => {
            try {
                const response = await getTodoListsByUserId(userId);
                setTodoLists(response.data);
                setLoading(false);
            } catch (err) {
                setError('Error fetching todo lists');
                setLoading(false);
            }
        };
        fetchTodoLists();
    }, [userId]);

    const handleShowModal = () => setShowModal(true);
    const handleCloseModal = () => setShowModal(false);

    const handleCreateTodoList = async () => {
        try {
            const newList = {
                title: newTodoList.title,
                description: newTodoList.description,
            };
            await createTodoList(userId, newList);
            setNewTodoList({ title: '', description: '' });
            handleCloseModal();
            const response = await getTodoListsByUserId(userId);
            setTodoLists(response.data);
        } catch (error) {
            setError('Error creating todo list');
        }
    };

    return (
        <div>
            <NavigationBar />
            <div className="container mt-5">
                <h1>User Dashboard</h1>
                <h3>Your Todo Lists:</h3>

                {loading ? (
                    <p>Loading...</p>
                ) : error ? (
                    <p>{error}</p>
                ) : (
                    <div>
                        {/* Todo Lists Table */}
                        <Table striped bordered hover className="mb-4">
                            <thead>
                            <tr className="table-success">
                                <th>ID</th>
                                <th>Title</th>
                                <th>Description</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            {todoLists.length === 0 ? (
                                <tr>
                                    <td colSpan="4" className="text-center">You have no todo lists.</td>
                                </tr>
                            ) : (
                                todoLists.map((list) => (
                                    <tr key={list.id}>
                                        <td>{list.id}</td>
                                        <td>{list.title}</td>
                                        <td>{list.description}</td>
                                        <td>
                                            <Button
                                                variant="outline-success"
                                                onClick={() => alert(`Fetching items for list: ${list.title}`)}
                                                className="mr-2"
                                            >
                                                View Items
                                            </Button>
                                        </td>
                                    </tr>
                                ))
                            )}
                            </tbody>
                        </Table>

                        {/* Button to create new todo list */}
                        <Button variant="primary" onClick={handleShowModal}>
                            Create New Todo List
                        </Button>
                    </div>
                )}

                {/* Modal for creating new Todo List */}
                <Modal show={showModal} onHide={handleCloseModal}>
                    <Modal.Header closeButton>
                        <Modal.Title>Create New Todo List</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <Form>
                            <Form.Group controlId="formTodoListTitle">
                                <Form.Label>Todo List Title</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Enter todo list title"
                                    name="title"
                                    value={newTodoList.title}
                                    onChange={handleChange}
                                />
                            </Form.Group>
                            <Form.Group controlId="formTodoListDescription">
                                <Form.Label>Todo List Description</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Enter todo list description"
                                    name="description"
                                    value={newTodoList.description}
                                    onChange={handleChange}
                                />
                            </Form.Group>
                        </Form>
                    </Modal.Body>
                    <Modal.Footer>
                        <Button variant="secondary" onClick={handleCloseModal}>
                            Close
                        </Button>
                        <Button variant="primary" onClick={handleCreateTodoList}>
                            Create Todo List
                        </Button>
                    </Modal.Footer>
                </Modal>
            </div>
        </div>
    );
};

const mapStateToProps = (state) => {
    return {
        userId: state.id,
    };
};

export default connect(mapStateToProps)(UserPage);
