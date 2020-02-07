import React from "react";

import { useBuilds } from "../util/hooks";

// Components
import WithLoader from "../components/withLoader";
import BuildsTable from "../components/buildsTable";

const Home = () => {
  const { builds, loading } = useBuilds();
  return (
    <>
      <h1 className="page-title">Home</h1>
      <h2 className="subtitle">Latest Builds</h2>
      <WithLoader loading={loading}>
        {builds && Object.keys(builds).length ? (
          <BuildsTable builds={builds} limit={2} />
        ) : (
          <p className="status-text">No builds to show yet</p>
        )}
      </WithLoader>
    </>
  );
};

export default Home;
