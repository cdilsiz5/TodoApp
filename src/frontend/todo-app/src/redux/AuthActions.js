import * as ACTIONS from "./Constants";
import TokenService from "../api/TokenService";
import {signin,signup} from "../api/ApiCalls";

export const logoutSuccess = () => {
  return {
    type: ACTIONS.LOGOUT_SUCCESS,
  };
};
export const loginSuccess = (authData) => {
  return {
    type: ACTIONS.LOGIN_SUCCESS,
    isLoggedIn: true,
    payload: authData,
  };
};
export const loginHandler = (credentials) => {
  return async function (dispatch) {
    const response = await signin(credentials);
    if (response.data.accessToken) {
      TokenService.setUser(response.data);
    }
    const userDto=response.data.userDto;
    const authState = {
      id :userDto.id,
      name:userDto.name,
      surname :userDto.surname,
      email: userDto.email,
      password: credentials.password,
    };
    dispatch(loginSuccess(authState));
    return response;
  };
};
export const signupHandler =(user)=>{
  return async function(dispatch){
    await dispatch(signup(user))
  }
}
export const updateSuccess =({userPhoneNumber,userEmail})=>{
  return {
    type :ACTIONS.UPDATE_SUCCESS,
    payload:{
      userEmail,
      userPhoneNumber
    }
  }
}
