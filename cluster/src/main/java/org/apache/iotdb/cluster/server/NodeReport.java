package org.apache.iotdb.cluster.server;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.iotdb.cluster.rpc.thrift.Node;

/**
 * A node report collects the current runtime information of the local node, which contains:
 * 1. The MetaMemberReport of the meta member.
 * 2. The DataMemberReports of each data member.
 */
public class NodeReport {

  private Node thisNode;
  private MetaMemberReport metaMemberReport;
  private List<DataMemberReport> dataMemberReportList;

  public NodeReport(Node thisNode) {
    this.thisNode = thisNode;
    dataMemberReportList = new ArrayList<>();
  }

  public void setMetaMemberReport(
      MetaMemberReport metaMemberReport) {
    this.metaMemberReport = metaMemberReport;
  }

  public void setDataMemberReportList(
      List<DataMemberReport> dataMemberReportList) {
    this.dataMemberReportList = dataMemberReportList;
  }

  @Override
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("Report of ").append(thisNode).append(System.lineSeparator());
    stringBuilder.append(metaMemberReport).append(System.lineSeparator());
    for (DataMemberReport dataMemberReport : dataMemberReportList) {
      stringBuilder.append(dataMemberReport).append(System.lineSeparator());
    }
    return stringBuilder.toString();
  }

  /**
   * A RaftMemberReport contains the character, leader, term, last log term/index of a raft member.
   */
  public static class RaftMemberReport {
    NodeCharacter character;
    Node leader;
    long term;
    long lastLogTerm;
    long lastLogIndex;
    boolean isReadOnly;
    long lastHeartbeatReceivedTime;

    public RaftMemberReport(NodeCharacter character, Node leader, long term, long lastLogTerm,
        long lastLogIndex, boolean isReadOnly, long lastHeartbeatReceivedTime) {
      this.character = character;
      this.leader = leader;
      this.term = term;
      this.lastLogTerm = lastLogTerm;
      this.lastLogIndex = lastLogIndex;
      this.isReadOnly = isReadOnly;
      this.lastHeartbeatReceivedTime = lastHeartbeatReceivedTime;
    }
  }

  /**
   * MetaMemberReport has no additional fields currently.
   */
  public static class MetaMemberReport extends RaftMemberReport {

    public MetaMemberReport(NodeCharacter character, Node leader, long term, long lastLogTerm,
        long lastLogIndex, boolean isReadOnly, long lastHeartbeatReceivedTime) {
      super(character, leader, term, lastLogTerm, lastLogIndex, isReadOnly, lastHeartbeatReceivedTime);
    }

    @Override
    public String toString() {
      return "MetaMemberReport{" +
          "character=" + character +
          ", Leader=" + leader +
          ", term=" + term +
          ", lastLogTerm=" + lastLogTerm +
          ", lastLogIndex=" + lastLogIndex +
          ", readOnly=" + isReadOnly +
          ", lastHeartbeat=" + new Date(lastHeartbeatReceivedTime) +
          '}';
    }
  }

  /**
   * A DataMemberReport additionally contains the header, so it can be told which group this
   * member belongs to.
   */
  public static class DataMemberReport extends RaftMemberReport {
    Node header;
    long headerLatency;

    public DataMemberReport(NodeCharacter character, Node leader, long term, long lastLogTerm,
        long lastLogIndex, Node header, boolean isReadOnly, long headerLatency,
        long lastHeartbeatReceivedTime) {
      super(character, leader, term, lastLogTerm, lastLogIndex, isReadOnly, lastHeartbeatReceivedTime);
      this.header = header;
      this.headerLatency = headerLatency;
    }

    @Override
    public String toString() {
      return "DataMemberReport{" +
          "header=" + header +
          ", character=" + character +
          ", Leader=" + leader +
          ", term=" + term +
          ", lastLogTerm=" + lastLogTerm +
          ", lastLogIndex=" + lastLogIndex +
          ", readOnly=" + isReadOnly +
          ", headerLatency=" + headerLatency +
          ", lastHeartbeat=" + new Date(lastHeartbeatReceivedTime) +
          '}';
    }
  }
}
