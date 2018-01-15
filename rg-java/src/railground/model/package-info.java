/**
 * Simple Java implementation of the RailGround Event-B model.
 * 
 * <p>Configuration</p>
 * <ul>
 * <li>1_Rails<dl>
 *   <dt>RAIL_ELEM</dt>
 *   <dd>Property RAIL_ELEM = [rail_elem_ID]*</dd>
 *   <dt>RAIL_TERM</dt>
 *   <dd>Property RAIL_TERM = [rail_term_ID]*</dd>
 *   <dt>RAIL_CTOR</dt>
 *   <dd>Hardcoded rail_ctor_IDs derived from rail_elem_ID or rail_term_ID</dd>
 *   <dt>RAIL_SGMT</dt>
 *   <dd>Hardcoded rail_sgmt_IDs derived from rail_elem_ID and rail_conn_ID</dd>
 *   <dt>RAIL_ELEM_CTOR</dt>
 *   <dd>Hardcoded for the given rail_elem</dd>
 *   <dt>RAIL_TERM_CTOR</dt>
 *   <dd>Hardcoded for the given rail_term</dd>
 *   <dt>RAIL_SGMT_CTOR</dt>
 *   <dd>Hardcoded for the given rail_elem</dd>
 *   <dt>RAIL_LINK</dt>
 *   <dd>Property RAIL_LINK = [rail_ctor_ID/rail_ctor_ID]*</dd>
 *   <dt>RAIL_ELEM_CONN_ALL</dt>
 *   <dd>Not implemented.</dd>
 *   <dt>RAIL_ELEM_CONN_DEFAULT</dt>
 *   <dd>Derived from RAIL_ELEM_POS_CONN and RAIL_ELEM_POS_DEFAULT</dd>
 *   <dt>RAIL_SGMT_ELEM</dt>
 *   <dd>Not implemented.</dd>
 * </dl></li>
 * <li>2_ElementPosition<dl>
 *   <dt>RAIL_POS_ENUM</dt>
 *   <dd>Enum with hardcoded values RAIL_POS_X, RAIL_POS_F, RAIL_POS_L, RAIL_POS_R</dd>
 *   <dt>RAIL_ELEM_POS_CONN</dt>
 *   <dd>Property rail_elem_ID.rail_pos_enum.RAIL_ELEM_POS_CONN = [rail_sgmt_ID]*</dd>
 *   <dt>RAIL_ELEM_POS_DEFAULT</dt>
 *   <dd>Property rail_elem_ID.RAIL_ELEM_POS_DEFAULT = rail_pos_enum</dd>
 * </dl></li>
 * <li>3_Paths<dl>
 *   <dt>PATH_ENUM</dt>
 *   <dd>Property PATH = [path_ID]*</dd>
 *   <dt>PATH_ELEM_POS</dt>
 *   <dd>Property path_ID.PATH_ELEM_POS = [rail_elem_ID/rail_pos_enum]*</dd>
 *   <dt>PATH_SGMT_ALL</dt>
 *   <dd>Property path_ID.PATH_SGMT_ALL = [rail_sgmt_ID]*</dd>
 *   <dt>PATH_CTOR_BEG</dt>
 *   <dd>Property path_ID.PATH_CTOR_BEG = rail_ctor_ID</dd>
 *   <dt>PATH_CTOR_END</dt>
 *   <dd>Property path_ID.PATH_CTOR_END = rail_ctor_ID</dd>
 *   <dt>PATH_SGMT_FST</dt>
 *   <dd>Property path_ID.PATH_SGMT_FST = rail_sgmt_ID</dd>
 *   <dt>PATH_SGMT_LST</dt>
 *   <dd>Property path_ID.PATH_SGMT_LST = rail_sgmt_ID</dd>
 *   <dt>PATH_SGMT_NXT</dt>
 *   <dd>Property path_ID.PATH_SGMT_NXT = [rail_sgmt_ID/rail_sgmt_ID]*</dd>
 * </dl></li>
 * <li>4_PathLifecycle<dl>
 *   <dt>PATH_EXCL</dt>
 *   <dd>Property path_ID.PATH_EXCL = [path_ID]*</dd>
 * </dl></li>
 * <li>5_Signals<dl>
 *   <dt>SIGNAL</dt>
 *   <dd>Property SIGNAL = [signal_ID]*</dd>
 *   <dt>SIGNAL_ASPECT_ENUM</dt>
 *   <dd>Enum with hardcoded values SIGNAL_ASPECT_STOP, SIGNAL_ASPECT_PROCEED</dd>
 *   <dt>SIGNAL_CTOR</dt>
 *   <dd>Property signal_ID.SIGNAL_CTOR = rail_ctor_ID</dd>
 *   <dt>SIGNAL_ASPECT_AVAIL</dt>
 *   <dd>Hardcoded to whole SIGNAL_ASPECT_ENUM</dd>
 *   <dt>SIGNAL_ASPECT_DEFAULT</dt>
 *   <dd>Hardcoded to SIGNAL_ASPECT_STOP</dd>
 * </dl></li>
 * <li>6_SignalDependency<dl>
 * </dl></li>
 * <li>7_VacancyDetection<dl>
 *   <dt>TVD_SECT</dt>
 *   <dd>Property TVD_SECT = [tvd_sect_ID]*</dd>
 *   <dt>TVD_STATE_ENUM</dt>
 *   <dd>Enum with hardcoded values TVD_STATE_VACANT, TVD_STATE_OCCUPIED</dd>
 *   <dt>TVD_SECT_SGMT</dt>
 *   <dd>Property tvd_sect_ID.TVD_SECT_SGMT = [rail_sgmt_ID]*</dd>
 *   <dt>TVD_SGMT_SECT</dt>
 *   <dd>Not implemented.</dd>
 * </dl></li>
 * <li>8_OccupationImpact<dl>
 * </dl></li>
 * </ul>
 *
 * @author tofische
*/
package railground.model;