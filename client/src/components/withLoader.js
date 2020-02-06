import React from "react";

import Spinner from "./spinner";

const withLoader = ({ loading, children }) =>
  loading ? <Spinner /> : children;

export default withLoader;
