import React, { useState, useEffect } from "react";
import firebase from "./firebase";

const BuildsContext = React.createContext();

const initialState = {}

export const BuildsProvider = props => {
  const [builds, setBuilds] = useState(initialState);

  useEffect(() => {
    firebase.getBuilds().then(res => setBuilds(res));
  }, []);

  return (
    <BuildsContext.Provider value={builds}>
      {props.children}
    </BuildsContext.Provider>
  );
};

export default BuildsContext;
