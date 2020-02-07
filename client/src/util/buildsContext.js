import React, { useState, useEffect } from "react";
import firebase from "./firebase";

const BuildsContext = React.createContext();

export const BuildsProvider = props => {
  const [builds, setBuilds] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    firebase.getBuilds().then(res => {
      console.log(res);
      setBuilds(res);
    });
  }, []);

  useEffect(() => firebase.subscribe(setBuilds), []);

  useEffect(() => {
    setLoading(!Boolean(builds));
  }, [builds]);

  return (
    <BuildsContext.Provider value={{ builds, loading }}>
      {props.children}
    </BuildsContext.Provider>
  );
};

export default BuildsContext;
