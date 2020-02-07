import React from "react";
import { Switch, Route } from "react-router-dom";

// Components
import Nav from "./components/nav";

// Pages
import Home from "./pages/home";
import Builds from "./pages/builds";
import SingleBuild from "./pages/singleBuild";
import NotFound from "./pages/notFound";

const App = () => {
  return (
    <>
      <Nav />
      <div className="container center">
        <Switch>
          <Route path="/" component={Home} exact />
          <Route path="/builds" component={Builds} exact />
          <Route path="/builds/:id" component={SingleBuild} exact />
          <Route component={NotFound} exact />
        </Switch>
      </div>
    </>
  );
};

export default App;
