import React from "react";

import { useBuilds } from "../util/hooks";

// Components
import WithLoader from "../components/withLoader";
import BuildsTable from "../components/buildsTable";

const Builds = () => {
  const { builds, loading } = useBuilds();
  return (
    <div>
      <h1 className="page-title">Builds</h1>
      <WithLoader loading={loading}>
        <BuildsTable builds={builds} />
      </WithLoader>
    </div>
  );
};

export default Builds;
