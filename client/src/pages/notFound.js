import React from "react";
import { Link } from "react-router-dom";

const notFound = () => {
  return (
    <div>
      <h1 className="page-title">404 - Could not find page</h1>
      <Link to="/" className="yellow-link bigger">
        Back to home
      </Link>
    </div>
  );
};

export default notFound;
