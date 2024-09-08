import { createStore, applyMiddleware } from "redux";
import AuthReducer from "./AuthReducer";
import SecureLS from "secure-ls";
import {thunk} from 'redux-thunk'
const securls = new SecureLS();

const getStateFromStorage =()=>{
    const todoAuth=securls.get('todo-auth')
    let stateInLocalStorage ={
        id:undefined,
        isLoggedIn: false,
        name: undefined,
        surname : undefined,
        email :undefined ,
        password :undefined,
        }
    if(todoAuth){
        return todoAuth
    }
    return stateInLocalStorage
}
const updateStateStorage= newState =>{
    securls.set('todo-auth',newState);

}

export const configureStore = () => {
    const store = createStore(
      AuthReducer,
      getStateFromStorage(),
      applyMiddleware(thunk)
    );
  
    store.subscribe(() => {
      updateStateStorage(store.getState());
    });
  
    return store;
  };
