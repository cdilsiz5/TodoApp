import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { getTodoListsByUserId, createTodoItem, updateTodoItem, deleteTodoItem } from "../api/ApiCalls";
import { Form, Button, Table, Modal } from "react-bootstrap";
import NavigationBar from "../components/Navbar";

const TodoItemPage = () => {
    const { listId } = useParams();
    const [todoList, setTodoList] = useState(null);
    const [newItem, setNewItem] = useState({
        title: "",
        description: "",
        status: "",
        priority: "",
        dueDate: ""
    });
    const [showModal, setShowModal] = useState(false);

    useEffect(() => {
        const fetchTodoList = async () => {
            try {
                const response = await getTodoListsByUserId(listId);
                setTodoList(response.data);
            } catch (error) {
                console.error("Error fetching todo list:", error);
            }
        };
        fetchTodoList();
    }, [listId]);

    const handleCreateTodoItem = async () => {
        try {
            const response = await createTodoItem(listId, newItem);
            setTodoList({
                ...todoList,
                todoItems: [...(todoList.todoItems || []), response.data],
            });
            setNewItem({
                title: "",
                description: "",
                status: "",
                priority: "",
                dueDate: "",
            });
            setShowModal(false);
        } catch (error) {
            console.error("Error creating todo item:", error);
        }
    };

    const handleUpdateTodoItem = async (id, updatedItem) => {
        try {
            await updateTodoItem(id, updatedItem);
            setTodoList((prevList) => ({
                ...prevList,
                todoItems: prevList.todoItems.map((item) =>
                    item.id === id ? updatedItem : item
                ),
            }));
        } catch (error) {
            console.error("Error updating todo item:", error);
        }
    };

    const handleDeleteTodoItem = async (id) => {
        try {
            await deleteTodoItem(id);
            setTodoList((prevList) => ({
                ...prevList,
                todoItems: prevList.todoItems.filter((item) => item.id !== id),
            }));
        } catch (error) {
            console.error("Error deleting todo item:", error);
        }
    };

    if (!todoList) {
        return <div>Loading...</div>;
    }

    return (
        <div>
            <NavigationBar />

            <h2>{todoList.title}</h2>
            <p>{todoList.description}</p>

            {todoList.todoItems && todoList.todoItems.length > 0 ? (
                <Table striped bordered hover>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Description</th>
                        <th>Status</th>
                        <th>Priority</th>
                        <th>Due Date</th>
                        <th>Completed</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    {todoList.todoItems.map((item) => (
                        <tr key={item.id} style={{ backgroundColor: item.completed ? 'lightgreen' : 'lightgrey' }}>
                            <td>{item.id}</td>
                            <td>{item.title}</td>
                            <td>{item.description}</td>
                            <td>{item.status}</td>
                            <td>{item.priority}</td>
                            <td>{item.dueDate}</td>
                            <td>{item.completed ? "Yes" : "No"}</td>
                            <td>
                                <Button
                                    variant="outline-success"
                                    onClick={() =>
                                        handleUpdateTodoItem(item.id, {
                                            ...item,
                                            completed: !item.completed,
                                        })
                                    }
                                >
                                    Toggle Completed
                                </Button>
                                <Button
                                    variant="outline-danger"
                                    onClick={() => handleDeleteTodoItem(item.id)}
                                >
                                    Delete
                                </Button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </Table>
            ) : (
                <p>No Items</p>
            )}

             <Button   variant="outline-success" onClick={() => setShowModal(true)}>
                Create New TodoItem
            </Button>

             <Modal show={showModal} onHide={() => setShowModal(false)}>
                <Modal.Header closeButton>
                    <Modal.Title>Create New Todo Item</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Group>
                            <Form.Label>Title</Form.Label>
                            <Form.Control
                                type="text"
                                value={newItem.title}
                                onChange={(e) =>
                                    setNewItem({ ...newItem, title: e.target.value })
                                }
                            />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Description</Form.Label>
                            <Form.Control
                                type="text"
                                value={newItem.description}
                                onChange={(e) =>
                                    setNewItem({ ...newItem, description: e.target.value })
                                }
                            />
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Status</Form.Label>
                            <Form.Control
                                as="select"
                                value={newItem.status}
                                onChange={(e) =>
                                    setNewItem({ ...newItem, status: e.target.value })
                                }
                            >
                                <option value="">Select status</option>
                                <option value="PENDING">Pending</option>
                                <option value="IN_PROGRESS">In Progress</option>
                                <option value="COMPLETED">Completed</option>
                            </Form.Control>
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Priority</Form.Label>
                            <Form.Control
                                as="select"
                                value={newItem.priority}
                                onChange={(e) =>
                                    setNewItem({ ...newItem, priority: e.target.value })
                                }
                            >
                                <option value="">Select priority</option>
                                <option value="LOW">Low</option>
                                <option value="MEDIUM">Medium</option>
                                <option value="HIGH">High</option>
                            </Form.Control>
                        </Form.Group>

                        <Form.Group>
                            <Form.Label>Due Date</Form.Label>
                            <Form.Control
                                type="date"
                                value={newItem.dueDate}
                                onChange={(e) =>
                                    setNewItem({ ...newItem, dueDate: e.target.value })
                                }
                            />
                        </Form.Group>
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={() => setShowModal(false)}>
                        Close
                    </Button>
                    <Button  variant="outline-success" onClick={handleCreateTodoItem}>
                        Create
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default TodoItemPage;
