import React from "react";

import { useBuild } from "../util/hooks";

import Spinner from "../components/spinner";

const SingleBuild = props => {
  const { build } = useBuild(props.match.params.id);

  if (!build) return <Spinner mt={8} />;

  return <h1 className="page-title">{build.data.title}</h1>;
};

export default SingleBuild;
