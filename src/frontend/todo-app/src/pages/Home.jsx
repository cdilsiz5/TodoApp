import React from 'react';
import { connect } from 'react-redux';
import NavigationBar from "../components/Navbar";

class Home extends React.Component {
    render() {
        const { isLoggedIn, name } = this.props;

        return (
            <div>
                <NavigationBar />

                <div className="container mt-5 text-center">
                    {isLoggedIn ? (
                        <h2>Welcome back, {name}! Manage your Todo lists below.</h2>
                    ) : (
                        <div>
                            <h2>Welcome to the Todo App!</h2>
                            <p>Please <a href="/signin">sign in</a> or <a href="/signup">sign up</a> to start managing your tasks.</p>
                        </div>
                    )}
                </div>
            </div>
        );
    }
}

const mapStateToProps = (state) => {
    return {
        isLoggedIn: state.isLoggedIn,
        name: state.name,
    };
};

export default connect(mapStateToProps)(Home);
