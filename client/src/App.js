import React, {useEffect, useContext} from "react";

import BuildsContext from "./util/buildsContext";

const App = () => {
  const builds = useContext(BuildsContext)

  useEffect(() => console.log(builds), [builds]);

  return <div className="App"></div>;
}

export default App;
