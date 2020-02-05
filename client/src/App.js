import React, {useEffect} from "react";

import firebase from "./util/firebase";

const App = () => {

  useEffect(() => {firebase.getBuilds()});

  return <div className="App"></div>;
}

export default App;
