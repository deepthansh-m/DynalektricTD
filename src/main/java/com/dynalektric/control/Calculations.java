package com.dynalektric.control;

import com.dynalektric.constants.ParameterNameConstants;
import com.dynalektric.model.Model;
import com.dynalektric.model.repositories.project.InputData;
import com.dynalektric.model.repositories.project.OutputData;
import java.lang.Math;
import java.security.PublicKey;
import java.util.Objects;

public class Calculations {
    Model model;

    private InputData inputData;
    private OutputData outputData;

    public Calculations() {
        model = Model.getSingleton();
    }

    public void beginCalculations() {

        this.inputData = model.getLoadedProjectInput();
        this.outputData = model.getOutputData();

        // vph and iph based on connection type for LV
        if(Objects.equals(inputData.CONNECTIONTYPELV, "STAR")) {
            vph_lv_star();
            iph_lv_star();
        }
        else {
            vph_lv_delta();
            iph_lv_delta();
        }

        // vph and iph based on connection type for HV
        if(Objects.equals(inputData.CONNECTIONTYPEHV, "STAR")) {
            vph_hv_star();
            iph_hv_star();
        }
        else {
            vph_hv_delta();
            iph_hv_delta();
        }

        v_t();
//        rated_voltage_LV();
//        rated_voltage_HV();
//        rated_current_LV();
//        rated_current_HV();
        cross_section_LV();
        cross_section_HV();
        current_density_LV();
        current_density_HV();
        turn_limb_LV();
        turn_limb_HV();
        turn_layer_LV();
        turn_layer_HV();
        wire_insulated_LV1();
        wire_insulated_LV2();
        wire_insulated_HV1();
        wire_insulated_HV2();
        wind_length_LV();
        wind_length_HV();
        wdg_lg_imp_calcu_LV();
        wdg_lg_imp_calcu_HV();
        limb_length_LV();
        limb_length_HV();
        wind_radial_depth_lv();
        wind_radial_depth_hv();
        net_cross_section();
        core_loss();
        spec_losses();
        core_d();
        total_core_w();
        total_core_d();
        id_w();
        id_d();
        lv_wdg();
        od_w();
        od_d();
        total_id_w();
        total_id_d();
        hv_wdg();
        total_od_w();
        total_od_d();
        c_dist();
        yoke_l();
        leads();
        limb_h();
        total_core_mass();
        calc_loss();
        r1();
        r2();
        r3();
        r4();
        perimeter1();
        perimeter2();
        perimeter3();
        perimeter4();
        mean_lg_lv();
        mean_lg_delta();
        mean_lg_hv();
        turn_length_lv();
        turn_length_hv();
        wire_length_lv();
        wire_length_hv();
        set_conductivity();
        resistance_lv();
        resistance_hv();
        conductor_lv1();
        conductor_lv2();
        conductor_hv1();
        conductor_hv2();
        h();
        b();
        kr();
        ls();
        delta_dash();
        ex();
        stray_loss_lv();
        stray_loss_hv();
        load_loss_lv();
        load_loss_hv();
        mass_of_conductor();
        lv1();
        hv_m();
        tank();
        obtained_loss();
        er();
        ek();
        s_am2_wdg_lv();
        s_am2_wdg_hv();
        w_m2_lv();
        w_m2_hv();
        wdg_temp_rise_lv();
        wdg_temp_rise_hv();
        gr_wdg_lv();
        gr_wdg_hv();
        core();
        core_sa();
        wdg_sa();
        sum_sa();
        sum_loss();
        theta_k();
        update_va();
        mass_limb();
        mass_limb_dash();
        mass_yoke();
        mass_yoke_dash();
        mass_corner();
        MASS_CORNER_DASH();
        gap_va();
        sum_va();
        nl_current_percentage();
        extra_nl_loss();
        l_active();
        b_active();
        h_active();
        l_mechanical();
        b_mechanical();
        h_mechanical();
        bom_core();
        bom_core_steel();
        bom_conductor();
        bom_leads();
        bom_insulation_fg();
        bom_connection_fg();
        bom_insulation_cl_h();
        bom_resin_v50();
        bom_misc();
        bom_total_mass();
        mass();
        mass_core_wdg();
        total_mass_w_o_encl();
        rmc();
        model.setOutputData(outputData);
    }

    //Write all the functions here

    public void vph_lv_star() {
        // Calculating VPH value for LV star Connection Type.
        outputData.VPH_LV = inputData.LINEVOLTSLV / Math.sqrt(3);
    }

    public void vph_hv_star() {
        // Calculating VPH value for HV star Connection Type.
        outputData.VPH_HV = inputData.LINEVOLTSHV / Math.sqrt(3);
    }

    public void iph_lv_star() {
        // Calculating IPH value for LV star Connection Type.
        outputData.IPH_LV = (inputData.KVA * 1000) / (Math.sqrt(3) * inputData.LINEVOLTSLV);
    }

    public void iph_hv_star() {
        // Calculating IPH value for HV star Connection Type.
        outputData.IPH_HV = (inputData.KVA * 1000) / (Math.sqrt(3) * inputData.LINEVOLTSHV);
    }

    public void vph_lv_delta() {
        // Calculating VPH value for LV delta Connection type.
        outputData.VPH_LV = inputData.LINEVOLTSLV;
    }

    public void vph_hv_delta() {
        //calculating VPH value for HV delta Connection type.
        outputData.VPH_HV = inputData.LINEVOLTSHV;

    }

    public void iph_lv_delta() {
        //calculating IPH value for LV delta Connection type.
        outputData.IPH_LV = (inputData.KVA * 1000) / (3 * outputData.VPH_LV);
    }

    public void iph_hv_delta() {
        // calculating  IPH value for HV delta Connection type.
        outputData.IPH_HV = (inputData.KVA * 1000) / (3 * outputData.VPH_HV);

    }

    public void v_t() {
        // Calculating the value of v/t.
        outputData.V_T = 1.01 * Math.sqrt(inputData.KVA / 3) * (inputData.K / 100);
    }

//    public void rated_voltage_LV() {
//        if (Objects.equals(inputData.CONNECTIONTYPELV, "DELTA")) {
//            outputData.RATED_VOLTAGE_LV = inputData.LINEVOLTSLV / Math.sqrt(3);
//        } else {
//            outputData.RATED_VOLTAGE_LV = inputData.LINEVOLTSLV;
//        }
//    }
//
//    public void rated_voltage_HV() {
//        if (Objects.equals(inputData.CONNECTIONTYPEHV, "DELTA")) {
//            outputData.RATED_VOLTAGE_HV = inputData.LINEVOLTSHV / Math.sqrt(3);
//        } else {
//            outputData.RATED_VOLTAGE_HV = inputData.LINEVOLTSHV;
//        }
//    }
//
//    public void rated_current_LV() {
//        if (Objects.equals(inputData.CONNECTIONTYPELV, "DELTA")) {
//            outputData.RATED_CURRENT_LV = (inputData.KVA * 1000) / (3 * outputData.RATED_VOLTAGE_LV);
//        } else {
//            outputData.RATED_CURRENT_LV = inputData.KVA * 1000 / Math.sqrt(3) * inputData.LINEVOLTSLV;
//        }
//    }
//
//    public void rated_current_HV() {
//        if (Objects.equals(inputData.CONNECTIONTYPEHV, "DELTA")) {
//            outputData.RATED_CURRENT_HV = (inputData.KVA * 1000) / (3 * outputData.RATED_VOLTAGE_HV);
//        } else {
//            outputData.RATED_CURRENT_HV = (inputData.KVA * 1000) / (Math.sqrt(3) * inputData.LINEVOLTSHV);
//        }
//    }

    public void cross_section_LV() {
        double para;
        if (Objects.equals(inputData.WIREBARELV1, inputData.WIREBARELV2)) {
            outputData.CROSS_SECTION_LV = inputData.WIREBARELV1 * inputData.WIREBARELV2 * 0.7854 ;
        } else {
            if (inputData.WIREBARELV2 <= 1.6)
            {
                para = 0.216;
            }
            else if (inputData.WIREBARELV2 >= 1.6 && inputData.WIREBARELV2 <= 2.24)
            {
                para = 0.363;
            }
            else if (inputData.WIREBARELV2 >= 2.25 && inputData.WIREBARELV2 <= 3.55)
            {
                para = 0.55;
            }
            else if (inputData.WIREBARELV2 >= 3.56 && inputData.WIREBARELV2 <=5.6)
            {
                para = 0.86;
            }
            else
            {
                para = 1.34;
            }
            outputData.CROSS_SECTION_LV = ((inputData.WIREBARELV1 * inputData.WIREBARELV2)-para) * inputData.NO_IN_PARALLEL_RA_LV1 * inputData.NO_IN_PARALLEL_RA_LV2;
        }
    }

    public void cross_section_HV() {
        double para;
        if (Objects.equals(inputData.WIREBAREHV1, inputData.WIREBAREHV2)) {
            outputData.CROSS_SECTION_HV = inputData.WIREBAREHV1 * inputData.WIREBAREHV2 * 0.7854;
        } else {
            if (inputData.WIREBAREHV2 <= 1.6)
            {
                para = 0.216;
            }
            else if (inputData.WIREBAREHV2 >= 1.6 && inputData.WIREBAREHV2 <= 2.24)
            {
                para = 0.363;
            }
            else if (inputData.WIREBAREHV2 >= 2.25 && inputData.WIREBAREHV2 <= 3.55)
            {
                para = 0.55;
            }
            else if (inputData.WIREBAREHV2 >= 3.56 && inputData.WIREBAREHV2 <=5.6)
            {
                para = 0.86;
            }
            else
            {
                para = 1.34;
            }
            outputData.CROSS_SECTION_HV = ((inputData.WIREBAREHV1 * inputData.WIREBAREHV2)-para) * inputData.NO_IN_PARALLEL_RA_HV1 * inputData.NO_IN_PARALLEL_RA_HV2;
        }
    }

    public void current_density_LV() {
        //calculating current density value for LV connection type.
        outputData.CURRENT_DENSITY_LV = outputData.IPH_LV / outputData.CROSS_SECTION_LV;
    }

    public void current_density_HV() {
        //calculating current density value for HV connection type.
        outputData.CURRENT_DENSITY_HV = outputData.IPH_HV / outputData.CROSS_SECTION_HV;
    }

    public void turn_limb_LV() {
        //calculating  turn/limb for LV connection type.
        outputData.TURN_LIMB_LV = (double) Math.round(outputData.VPH_LV / outputData.V_T);
    }

    public void turn_limb_HV() {
        //calculating  turn/limb value for HV connection type.
        outputData.TURN_LIMB_HV = (double) Math.round(outputData.VPH_HV / outputData.V_T);
    }

    public void turn_layer_LV() {
        //calculating  turn/layer for LV connection type.
        outputData.TURN_LAYER_LV = Math.ceil(outputData.TURN_LIMB_LV / inputData.LAYER_LV);
    }

    public void turn_layer_HV() {
        //calculating  turn/layer value for HV connection type.
        outputData.TURN_LAYER_HV = Math.ceil(outputData.TURN_LIMB_HV / inputData.LAYER_HV);
    }

    public void wire_insulated_LV1() {
        double answer = inputData.WIREBARELV1;
        if (Objects.equals(inputData.WINDINGTYPELV, "STRIP")) {
            answer += inputData.INSULATION_LV;
        }

        outputData.WIRE_INSULATED_LV1 = answer;
    }

    public void wire_insulated_LV2() {
        double answer = inputData.WIREBARELV2;
        if (Objects.equals(inputData.WINDINGTYPELV, "STRIP")) {
            answer += inputData.INSULATION_LV;
        }

        outputData.WIRE_INSULATED_LV2 = answer;
    }

    public void wire_insulated_HV1() {
        double answer = inputData.WIREBAREHV1;
        if (Objects.equals(inputData.WINDINGTYPEHV, "STRIP")) {
            answer += inputData.INSULATION_HV;
        }

        outputData.WIRE_INSULATED_HV1 = answer;
    }

    public void wire_insulated_HV2() {
        double answer = inputData.WIREBAREHV1;
        if (Objects.equals(inputData.WINDINGTYPEHV, "STRIP")) {
            answer += inputData.INSULATION_HV;
        }

        outputData.WIRE_INSULATED_HV2 = answer;
    }

    public void wind_length_LV() {
        double answer = outputData.WIRE_INSULATED_LV1 * inputData.NO_IN_PARALLEL_RA_LV2;
        double value = outputData.TURN_LAYER_LV;
        if (Objects.equals(inputData.WINDINGTYPELV, "STRIP")) {
            value += 1;
        }
        answer = answer * value;
        answer = answer + inputData.TRANSPOSITION_LV + inputData.COMP_GAP_LV + inputData.OIL_DUCTS_AXIAL_LV;
        answer = Math.round(answer);

        outputData.WIND_LENGTH_LV = answer;
    }

    public void wind_length_HV() {
        double answer = outputData.WIRE_INSULATED_HV1 * inputData.NO_IN_PARALLEL_RA_HV2;
        double value = outputData.TURN_LAYER_HV;
        if (Objects.equals(inputData.WINDINGTYPEHV, "STRIP")) {
            value += 1;
        }
        answer = answer * value;
        answer = answer + inputData.TRANSPOSITION_HV + inputData.COMP_GAP_HV;
        answer = Math.round(answer);

        outputData.WIND_LENGTH_HV = answer;
    }

    public void wdg_lg_imp_calcu_LV() {
        double answer = outputData.WIND_LENGTH_LV;
        if (Objects.equals(inputData.WINDINGTYPELV, "STRIP")) {
            answer = answer - (outputData.WIRE_INSULATED_LV1 * inputData.NO_IN_PARALLEL_RA_LV2);
        }
        outputData.WDG_LG_IMP_CALCU_LV = answer;
    }

    public void wdg_lg_imp_calcu_HV() {
        double answer = outputData.WIND_LENGTH_HV;
        if (ParameterNameConstants.WINDINGTYPEHV == 1.0) {
            answer = answer - (outputData.WIRE_INSULATED_HV1 * inputData.NO_IN_PARALLEL_RA_HV2);

        }
        outputData.WDG_LG_IMP_CALCU_HV = answer;
    }

    public void limb_length_LV() {
        outputData.LIMB_LENGTH_LV = outputData.WIND_LENGTH_LV + inputData.END_CLEARANCES_LV;
    }

    public void limb_length_HV() {
        outputData.LIMB_LENGTH_HV = outputData.WIND_LENGTH_HV + inputData.END_CLEARANCES_HV;
    }

    public void wind_radial_depth_lv() {
        double answer = outputData.WIRE_INSULATED_LV2 * inputData.NO_IN_PARALLEL_RA_LV1 * inputData.LAYER_LV;
        answer = answer + inputData.OIL_DUCTS_RADIAL_LV1 * inputData.OIL_DUCTS_RADIAL_LV2;
        answer = (answer + inputData.INSULATION_BETWEEN_LAYERS_LV * (inputData.LAYER_LV - 1)) * 1.05;
        answer = Math.round(answer);
        outputData.WIND_RADIAL_DEPTH_LV = answer;
    }

    public void wind_radial_depth_hv() {
        outputData.WIND_RADIAL_DEPTH_HV = Math.round(((outputData.WIRE_INSULATED_HV2 * inputData.NO_IN_PARALLEL_RA_HV1 * inputData.LAYER_HV + inputData.OIL_DUCTS_RADIAL_HV1 * inputData.OIL_DUCTS_RADIAL_HV2 + inputData.INSULATION_BETWEEN_LAYERS_HV * (inputData.LAYER_HV - 1))*1.05));
    }

    public void net_cross_section() {
        outputData.NET_CROSS_SECTION = outputData.V_T / ((4.44 * inputData.FREQUENCY * inputData.FLUX_DENSITY) / 10000);
    }

    public void core_loss(){
        double w_kg = 0;
        if (inputData.FLUX_DENSITY <= 0.600) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.135;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.110;
            }
        } else if (inputData.FLUX_DENSITY <= 0.625) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.475;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.15;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.12;
            }
        } else if (inputData.FLUX_DENSITY <= 0.650) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.500;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.160;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.130;
            }
        } else if (inputData.FLUX_DENSITY <= 0.675) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.525;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.170;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.140;
            }
        } else if (inputData.FLUX_DENSITY <= 0.700) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.550;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.180;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.150;
            }
        } else if (inputData.FLUX_DENSITY <= 0.725) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.575;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.190;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.160;
            }
        } else if (inputData.FLUX_DENSITY <= 0.750) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.600;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.200;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.170;
            }
        } else if (inputData.FLUX_DENSITY <= 0.775) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.625;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.218;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.185;
            }
        } else if (inputData.FLUX_DENSITY <= 0.800) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.650;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.235;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.195;
            }
        } else if (inputData.FLUX_DENSITY <= 0.825) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.675;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.258;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.210;
            }
        } else if (inputData.FLUX_DENSITY <= 0.850) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.725;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.260;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.220;
            }
        } else if (inputData.FLUX_DENSITY <= 0.875) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.765;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.275;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.230;
            }
        } else if (inputData.FLUX_DENSITY <= 0.900) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.800;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.290;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.240;
            }
        } else if (inputData.FLUX_DENSITY <= 0.925) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.825;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.310;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.255;
            }
        } else if (inputData.FLUX_DENSITY <= 0.950) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.850;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.330;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.270;
            }
        } else if (inputData.FLUX_DENSITY <= 0.975) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.900;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.345;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.285;
            }
        } else if (inputData.FLUX_DENSITY <= 1.000) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 0.95;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.360;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.300;
            }
        } else if (inputData.FLUX_DENSITY <= 1.025) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.000;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.380;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.310;
            }
        } else if (inputData.FLUX_DENSITY <= 1.050) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.050;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.400;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.320;
            }
        } else if (inputData.FLUX_DENSITY <= 1.075) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.100;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.420;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.335;
            }
        } else if (inputData.FLUX_DENSITY <= 1.100) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.150;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.440;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.350;
            }
        } else if (inputData.FLUX_DENSITY <= 1.125) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.200;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.460;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.370;
            }
        } else if (inputData.FLUX_DENSITY <= 1.150) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.250;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.480;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.390;
            }
        } else if (inputData.FLUX_DENSITY <= 1.175) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.300;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.495;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.405;
            }
        } else if (inputData.FLUX_DENSITY <= 1.200) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.350;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.510;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.420;
            }
        } else if (inputData.FLUX_DENSITY <= 1.225) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.425;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.535;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.440;
            }
        } else if (inputData.FLUX_DENSITY <= 1.250) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.500;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.560;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.460;
            }
        } else if (inputData.FLUX_DENSITY <= 1.275) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.550;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.580;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.475;
            }
        } else if (inputData.FLUX_DENSITY <= 1.300) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.600;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.600;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.490;
            }
        } else if (inputData.FLUX_DENSITY <= 1.325) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.675;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.625;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.505;
            }
        } else if (inputData.FLUX_DENSITY <= 1.350) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.750;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.65;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.52;
            }
        } else if (inputData.FLUX_DENSITY <= 1.375) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.825;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.675;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.540;
            }
        } else if (inputData.FLUX_DENSITY <= 1.400) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.900;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.700;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.560;
            }
        } else if (inputData.FLUX_DENSITY <= 1.425) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 1.975;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.73;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.580;
            }
        } else if (inputData.FLUX_DENSITY <= 1.450) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.050;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.760;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.600;
            }
        } else if (inputData.FLUX_DENSITY <= 1.475) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.150;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.795;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.625;
            }
        } else if (inputData.FLUX_DENSITY <= 1.500) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.250;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.830;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.650;
            }
        } else if (inputData.FLUX_DENSITY <= 1.525) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.35;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.865;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.675;
            }
        } else if (inputData.FLUX_DENSITY <= 1.550) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.900;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.700;
            }
        } else if (inputData.FLUX_DENSITY <= 1.575) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 0.950;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.730;
            }
        } else if (inputData.FLUX_DENSITY <= 1.6) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 1.000;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.760;
            }
        } else if (inputData.FLUX_DENSITY <= 1.625) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 1.060;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.790;
            }
        } else if (inputData.FLUX_DENSITY <= 1.650) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 1.120;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.820;
            }
        } else if (inputData.FLUX_DENSITY <= 1.675) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 1.185;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.860;
            }
        } else if (inputData.FLUX_DENSITY <= 1.7) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 1.250;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.900;
            }
        } else if (inputData.FLUX_DENSITY <= 1.725) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 1.325;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 0.950;
            }
        } else if (inputData.FLUX_DENSITY <= 1.750) {
            if(Objects.equals(inputData.STEEL_GRADE, "CRNO-35")) {
                w_kg = 2.450;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "M4-27")) {
                w_kg = 1.400;
            }
            else if(Objects.equals(inputData.STEEL_GRADE, "MOH-23")) {
                w_kg = 1.000;
            }
        }
        outputData.W_KG =w_kg;
    }

    public void spec_losses() {
        outputData.SPEC_LOSSES = 1.32 * outputData.W_KG;
    }

    public void core_d() {
        outputData.CORE_D = (double)Math.round(((outputData.NET_CROSS_SECTION / inputData.STACKING_FACTOR) / (inputData.CORE_W * 0.1)) * 10);
    }

    public void total_core_w() {
        outputData.TOTAL_CORE_W = inputData.CORE_W + inputData.LIMB_PLATE_W;
    }

    public void total_core_d() {
        outputData.TOTAL_CORE_D = outputData.CORE_D + inputData.LIMB_PLATE_D;
    }

    public void id_w() {
        outputData.ID_W = outputData.TOTAL_CORE_W + inputData.GAP_W;
    }

    public void id_d() {
        outputData.ID_D = outputData.TOTAL_CORE_D + inputData.GAP_D;
    }

    public void lv_wdg() {
        outputData.LV_WDG = 2 * outputData.WIND_RADIAL_DEPTH_HV;
    }

    public void od_w() {
        outputData.OD_W = outputData.ID_W + outputData.LV_WDG;
    }

    public void od_d() {
        outputData.OD_D = outputData.ID_D + outputData.LV_WDG;
    }

    public void total_id_w() {
        outputData.TOTAL_ID_W = outputData.OD_W + inputData.DELTA_W;
    }

    public void total_id_d() {
        outputData.TOTAL_ID_D = outputData.OD_D + inputData.DELTA_D;
    }

    public void hv_wdg() {
        outputData.HV_WDG = 2 * outputData.WIND_RADIAL_DEPTH_LV;
    }

    public void total_od_w() {
        outputData.TOTAL_OD_W = outputData.TOTAL_ID_W + outputData.HV_WDG;
    }

    public void total_od_d() {
        outputData.TOTAL_OD_D = outputData.TOTAL_ID_D + outputData.HV_WDG;
    }

    public void c_dist() {
        outputData.C_DIST = outputData.TOTAL_OD_W + inputData.AM_W;
    }

    public void yoke_l() {
        outputData.YOKE_L = (2 * outputData.C_DIST) + inputData.CORE_W;
    }

    public void leads() {
        outputData.LEADS = outputData.TOTAL_OD_D;
    }

    public void limb_h() {
        outputData.LIMB_H = outputData.LIMB_LENGTH_LV;
    }

    public void total_core_mass() {
        outputData.TOTAL_CORE_MASS = (((outputData.LIMB_H * 3) + (outputData.YOKE_L * 2)) * 0.1 * outputData.NET_CROSS_SECTION * 7.65) / 1000;
    }

    public void calc_loss() {
        outputData.CALC_LOSS = outputData.TOTAL_CORE_MASS * outputData.SPEC_LOSSES * inputData.CORE_BLDG_FACTOR;
    }

    public void r1() {
        outputData.R1 = inputData.GAP_W / 2.0;
    }

    public void r2() {
        outputData.R2 = outputData.R1 + outputData.WIND_RADIAL_DEPTH_HV;
    }

    public void r3() {
        outputData.R3 = outputData.R2 + ((double) inputData.DELTA_W / 2);
    }

    public void r4() {
        outputData.R4 = outputData.R3 + outputData.WIND_RADIAL_DEPTH_LV;
    }

    public void perimeter1() {
        outputData.PERIMETER1 = (double)Math.round((2 * inputData.CORE_W) + (outputData.TOTAL_CORE_D * 2) + (2 * 3.1416 * outputData.R1));
    }

    public void perimeter2() {
        outputData.PERIMETER2 = (double)Math.round((2 * inputData.CORE_W) + (outputData.TOTAL_CORE_D * 2) + (2 * 3.1416 * outputData.R2));
    }

    public void perimeter3() {
        outputData.PERIMETER3 = (double)Math.round((2 * inputData.CORE_W) + (outputData.TOTAL_CORE_D * 2) + (2 * 3.1416 * outputData.R3));
    }

    public void perimeter4() {
        outputData.PERIMETER4 = (double)Math.round((2 * inputData.CORE_W) + (outputData.TOTAL_CORE_D * 2) + (2 * 3.1416 * outputData.R4));
    }

    public void mean_lg_lv() {
        outputData.MEAN_LG_LV = (outputData.PERIMETER1 + outputData.PERIMETER2) / 2.0;
    }

    public void mean_lg_delta() {
        outputData.MEAN_LG_DELTA = (outputData.PERIMETER2 + outputData.PERIMETER3) / 2.0;
    }

    public void mean_lg_hv() {
        outputData.MEAN_LG_HV = (outputData.PERIMETER3 + outputData.PERIMETER4) / 2.0;
    }

    public void turn_length_lv() {
        outputData.TURN_LENGTH_LV = outputData.MEAN_LG_HV / 1000;
    }

    public void turn_length_hv() {
        outputData.TURN_LENGTH_HV = outputData.MEAN_LG_LV / 1000;
    }

    public void wire_length_lv() {
        outputData.WIRE_LENGTH_LV = outputData.TURN_LENGTH_LV * outputData.TURN_LIMB_LV;
    }

    public void wire_length_hv() {
        outputData.WIRE_LENGTH_HV = outputData.TURN_LENGTH_HV * outputData.TURN_LIMB_HV;
    }
    public void set_conductivity(){
        if (Objects.equals(inputData.CONDUCTOR, "COPPER"))
        {
            if (inputData.WIND_TEMP == 90)
            {
                inputData.CONDUCTIVITY = 44.7;
            } else if (inputData.WIND_TEMP == 115) {
                inputData.CONDUCTIVITY = 42.76;
            }
        }
        else
        {
            if (inputData.WIND_TEMP == 90)
            {
                inputData.CONDUCTIVITY = 28;
            }
            else if (inputData.WIND_TEMP == 115) {
                inputData.CONDUCTIVITY = 26.73;
            }
        }
    }

    public void resistance_lv() {
        outputData.RESISTANCE_LV = outputData.WIRE_LENGTH_LV / (outputData.CROSS_SECTION_LV * inputData.CONDUCTIVITY);
    }

    public void resistance_hv() {
        outputData.RESISTANCE_HV = outputData.WIRE_LENGTH_HV / (outputData.CROSS_SECTION_HV * inputData.CONDUCTIVITY);
    }

    public void conductor_lv1() {
        double answer = outputData.WIRE_LENGTH_LV * outputData.CROSS_SECTION_LV;
        if(Objects.equals(inputData.CONDUCTOR, "COPPER")) {
            answer = answer * 8.89;
        }
        else {
            answer = answer * 2.7;
        }
        answer = answer * (3 / 1000.0);
        outputData.CONDUCTOR_LV1 = answer;
    }

    public void conductor_lv2() {
        double answer = outputData.CONDUCTOR_LV1;
        double value = ((outputData.WIRE_INSULATED_LV1 * outputData.WIRE_INSULATED_LV2) - (inputData.WIREBARELV1 * inputData.WIREBARELV2));
        value = value * inputData.NO_IN_PARALLEL_RA_LV1;
        value = value * inputData.NO_IN_PARALLEL_RA_LV2;
        value = value * outputData.WIRE_LENGTH_LV;
        value = value * 2 * 3 * 0.001;
        outputData.CONDUCTOR_LV2 = answer + value;
    }

    public void conductor_hv1() {
        double answer = outputData.WIRE_LENGTH_HV * outputData.CROSS_SECTION_HV;
        if(Objects.equals(inputData.CONDUCTOR, "COPPER")) {
            answer = answer * 8.89;
        }
        else {
            answer = answer * 2.7;
        }
        answer = answer * (3 / 1000.0);
        outputData.CONDUCTOR_HV1 = answer;
    }

    public void conductor_hv2() {
        double answer = outputData.CONDUCTOR_HV1;
        double value = ((outputData.WIRE_INSULATED_HV1 * outputData.WIRE_INSULATED_HV2) - (inputData.WIREBAREHV1 * inputData.WIREBAREHV2));
        value = value * inputData.NO_IN_PARALLEL_RA_HV1;
        value = value * inputData.NO_IN_PARALLEL_RA_HV2;
        value = value * outputData.WIRE_LENGTH_HV;
        value = value * 2 * 3 * 0.001;
        outputData.CONDUCTOR_HV2 = answer + value;
    }

    public void h() {
        outputData.H = (outputData.WDG_LG_IMP_CALCU_LV + outputData.WDG_LG_IMP_CALCU_HV) / 2.0;
    }

    public void b() {
        outputData.B = outputData.WIND_RADIAL_DEPTH_HV + ( (double) inputData.DELTA_D / 2 ) + outputData.WIND_RADIAL_DEPTH_LV;
    }

    public void kr() {
        outputData.KR = 1 - (1 / ((outputData.H / outputData.B) * 3.1416));
    }

    public void ls() {
        outputData.LS = outputData.H / outputData.KR;
    }

    public void delta_dash() {
        double answer = (inputData.DELTA_D / 2.0) * outputData.MEAN_LG_DELTA;
        answer = answer + ((outputData.WIND_RADIAL_DEPTH_HV * outputData.MEAN_LG_LV) + (outputData.WIND_RADIAL_DEPTH_LV * outputData.MEAN_LG_HV)) / 3.0;
        outputData.DELTA_DASH = answer;
    }

    public void ex() {
        double answer = 8 * 3.1416 * 3.1416;
        answer = answer * inputData.FREQUENCY;
        answer = answer * outputData.IPH_HV;
        answer = answer * outputData.TURN_LIMB_HV;
        answer = answer * outputData.DELTA_DASH * 1e-8;
        answer = answer / (outputData.LS * outputData.V_T);
        outputData.EX = answer;
    }

    public void stray_loss_lv() {
        double answer = inputData.WIREBARELV2 / 10;
        double value = ((inputData.WIREBARELV2 * (outputData.TURN_LAYER_LV + 1) * outputData.KR) / outputData.WIND_LENGTH_LV);
        value = Math.sqrt(value);
        if(Objects.equals(inputData.CONDUCTOR, "COPPER")) {
            value = value * 0.9622;
        }
        else {
            value = value * 0.7618;
        }
        answer = answer * value;
        answer = Math.pow(answer, 4);

        double result = Math.pow(inputData.LAYER_LV * inputData.NO_IN_PARALLEL_RA_LV1, 2) / 9;
        answer = answer * result * 100;
        outputData.STRAY_LOSS_LV = answer;
    }

    public void stray_loss_hv() {
        double answer = inputData.WIREBAREHV2 / 10;
        double value = ((inputData.WIREBAREHV2 * (outputData.TURN_LAYER_HV + 1) * outputData.KR) / outputData.WIND_LENGTH_HV);
        value = Math.sqrt(value);
        if(Objects.equals(inputData.CONDUCTOR, "COPPER")) {
            value = value * 0.9622;
        }
        else {
            value = value * 0.7618;
        }
        answer = answer * value;
        answer = Math.pow(answer, 4);


        double result = Math.pow(inputData.LAYER_HV * inputData.NO_IN_PARALLEL_RA_HV1, 2) / 9;
        answer = answer * result * 100;
        outputData.STRAY_LOSS_HV = answer;
    }

    public void load_loss_lv() {
        outputData.LOAD_LOSS_LV = outputData.IPH_LV * outputData.IPH_LV * outputData.RESISTANCE_LV * 3 * (1 + (outputData.STRAY_LOSS_LV * 0.01));
    }

    public void load_loss_hv() {
        outputData.LOAD_LOSS_HV = outputData.IPH_HV * outputData.IPH_HV * outputData.RESISTANCE_HV * 3 * (1 + (outputData.STRAY_LOSS_HV * 0.01));
    }

    public void mass_of_conductor() {
        outputData.MASS_OF_CONDUCTOR = outputData.CONDUCTOR_LV2 + outputData.CONDUCTOR_HV2;
    }

    public void lv1() {
        outputData.LV1 = outputData.LOAD_LOSS_LV;
    }

    public void hv_m() {
        outputData.HV_M = outputData.LOAD_LOSS_HV;
    }

    public void tank() {
        outputData.TANK = Math.ceil(inputData.KVA * 3);
    }

    public void obtained_loss() {
        outputData.OBTAINED_LOSS = outputData.LV1 + outputData.HV_M + outputData.TANK;
    }

    public void er() {
        outputData.ER = (outputData.OBTAINED_LOSS / inputData.KVA) * 0.1;
    }

    public void ek() {
        outputData.EK = Math.sqrt(Math.pow(outputData.ER, 2) + Math.pow(outputData.EX, 2));
    }

    public void s_am2_wdg_lv() {
        outputData.S_AM2_WDG_LV = (outputData.TURN_LENGTH_LV * (outputData.WDG_LG_IMP_CALCU_LV / 1000) * (2 + (2 * inputData.OIL_DUCTS_RADIAL_LV1)));
    }

    public void s_am2_wdg_hv() {
        outputData.S_AM2_WDG_HV = (outputData.TURN_LENGTH_HV * (outputData.WDG_LG_IMP_CALCU_HV / 1000) * (2 + (2 * inputData.OIL_DUCTS_RADIAL_HV1)));
    }

    public void w_m2_lv() {
        outputData.W_M2_LV = (outputData.LOAD_LOSS_LV / (3 * outputData.S_AM2_WDG_LV));
    }

    public void w_m2_hv() {
        outputData.W_M2_HV = (outputData.LOAD_LOSS_HV / (3 * outputData.S_AM2_WDG_HV));
    }

    public void wdg_temp_rise_lv() {
        outputData.WDG_TEMP_RISE_LV = 15.0 + (double) Math.round(outputData.W_M2_LV / 7.0);
    }

    public void wdg_temp_rise_hv() {
        outputData.WDG_TEMP_RISE_HV = 15.0 + (outputData.W_M2_HV / 4.0);
    }

    public void gr_wdg_lv() {
        outputData.GR_WDG_LV = outputData.WDG_TEMP_RISE_LV;
    }

    public void gr_wdg_hv() {
        outputData.GR_WDG_HV = outputData.WDG_TEMP_RISE_HV;
    }

    public void core() {
        double answer1 = ((2 * inputData.CORE_W) + outputData.CORE_D) * outputData.YOKE_L * 2;
        double answer2 = inputData.CORE_W * outputData.CORE_D * 4;
        double answer3 = (inputData.CORE_W + outputData.CORE_D) * 2 * outputData.LIMB_LENGTH_LV * 3;

        double result = answer1 + answer2 + answer3;
        outputData.CORE = result * 0.000001;
    }

    public void core_sa() {
        double answer = ((2 * inputData.CORE_W) + outputData.CORE_D) * outputData.YOKE_L;
        answer += (inputData.CORE_W * outputData.CORE_D * 4);
        answer = answer * 0.01;
        outputData.CORE_SA = answer;
    }
    public void wdg_sa() {
        outputData.WDG_SA = outputData.MEAN_LG_HV * outputData.WDG_LG_IMP_CALCU_LV * 0.01 * 3;
    }
    public  void sum_sa() {
        outputData.SUM_SA = outputData.CORE_SA + outputData.WDG_SA;
    }
    public void sum_loss() {
        outputData.SUM_LOSS = outputData.CALC_LOSS + outputData.OBTAINED_LOSS;
    }
    public  void theta_k() {
        double answer = 450 * Math.pow((outputData.SUM_LOSS / outputData.SUM_SA),0.826);
        outputData.THETA_K = (double)Math.round(answer);
    }

    public void update_va(){
        if (inputData.FLUX_DENSITY <= 1.25){
            inputData.VA_CM = 0.639;
            inputData.VA_KG = 0.65;
        }
        else if (inputData.FLUX_DENSITY <= 1.30){
            inputData.VA_CM = 0.853;
            inputData.VA_KG = 0.7;
        }
        else if (inputData.FLUX_DENSITY <= 1.35){
            inputData.VA_CM = 1.095;
            inputData.VA_KG = 0.76;
        }
        else if (inputData.FLUX_DENSITY <= 1.40){
            inputData.VA_CM = 1.37;
            inputData.VA_KG = 0.82;
        }
        else if (inputData.FLUX_DENSITY <= 1.45){
            inputData.VA_CM = 1.66;
            inputData.VA_KG = 0.9;
        }
        else if (inputData.FLUX_DENSITY <= 1.50){
            inputData.VA_CM = 1.98;
            inputData.VA_KG = 1.03;
        }
        else if (inputData.FLUX_DENSITY <= 1.55){
            inputData.VA_CM = 2.34;
            inputData.VA_KG = 1.2;
        }
        else if (inputData.FLUX_DENSITY <= 1.60){
            inputData.VA_CM = 2.74;
            inputData.VA_KG = 1.45;
        }
        else if (inputData.FLUX_DENSITY <= 1.65){
            inputData.VA_CM = 3.19;
            inputData.VA_KG = 1.85;
        }
        else if (inputData.FLUX_DENSITY <= 1.70){
            inputData.VA_CM = 3.72;
            inputData.VA_KG = 2.5;
        }
        else if (inputData.FLUX_DENSITY <= 1.75) {
            inputData.VA_CM = 4.37;
            inputData.VA_KG = 4;
        }
    }
    public  void mass_limb() {
        outputData.MASS_LIMB = inputData.CORE_W * outputData.CORE_D * inputData.STACKING_FACTOR * outputData.LIMB_H * 3 * 7.65 * 0.000001;
    }

    public void mass_limb_dash() {
        outputData.MASS_LIMB_DASH = inputData.VA_KG * outputData.MASS_LIMB;
    }

    public void mass_yoke() {
        outputData.MASS_YOKE = inputData.CORE_W * outputData.CORE_D * (outputData.C_DIST - inputData.CORE_W) * inputData.STACKING_FACTOR * 4 * 7.65 * 0.000001;
    }

    public void mass_yoke_dash() {
        outputData.MASS_YOKE_DASH = inputData.VA_KG * outputData.MASS_YOKE;
    }

    public void mass_corner() {
        outputData.MASS_CORNER = Math.pow(inputData.CORE_W,2) * outputData.CORE_D * inputData.STACKING_FACTOR * 6 * 7.65 * 0.000001;
    }
    public void MASS_CORNER_DASH() {
        outputData.MASS_CORNER_DASH = 6 * inputData.VA_KG * outputData.MASS_CORNER;
    }

    public void gap_va() {
        outputData.GAP_VA = 6 * inputData.VA_CM * inputData.CORE_W * outputData.CORE_D * inputData.STACKING_FACTOR * 0.01;
    }

    public void sum_va() {
        outputData.SUM_VA = outputData.MASS_LIMB_DASH + outputData.MASS_YOKE_DASH + outputData.MASS_CORNER_DASH + outputData.GAP_VA;
    }

    public void nl_current_percentage() {
        outputData.NL_CURRENT_PERCENTAGE = outputData.SUM_VA / (inputData.KVA * 1000) * 100;
    }

    public void extra_nl_loss() {
        outputData.EXTRA_NL_LOSS = Math.pow(((outputData.NL_CURRENT_PERCENTAGE / 100) * outputData.IPH_HV), 2) * outputData.RESISTANCE_HV * 3;
    }

    public void l_active() {
        outputData.L_ACTIVE = (double)Math.round((2 * outputData.C_DIST + (outputData.TOTAL_OD_W - inputData.CORE_W)) / 5.0) * 5;
    }

    public void b_active() {
        outputData.B_ACTIVE = (double)Math.round(outputData.LEADS / 5.0) * 5;
    }

    public void h_active() {
        outputData.H_ACTIVE = (double)Math.round((5 + 2 * inputData.CORE_W + outputData.LIMB_H) / 5.0) * 5.0;
    }
    public void l_mechanical() {
        double answer = Math.round((outputData.L_ACTIVE +  (2*250) ) / 5);
        outputData.L_MECHANICAL = answer * 5;
    }
    public  void b_mechanical() {
        double answer = Math.round((outputData.B_ACTIVE + (2*260) ) / 5 );
        outputData.B_MECHANICAL = answer * 5;
    }
    public void h_mechanical() {
        double answer = Math.round((outputData.H_ACTIVE  + 250 + 75 ) / 5);
        outputData.H_MECHANICAL = answer * 5;
    }
    public void bom_core() {
        outputData.BOM_CORE = outputData.TOTAL_CORE_MASS;
    }
    public void bom_core_steel() {
        outputData.BOM_CORE_STEEL = (double)Math.round(outputData.BOM_CORE * 0.1);
    }
    public void bom_conductor() {
        outputData.BOM_CONDUCTOR_WT = (double)Math.round((outputData.CONDUCTOR_LV2 + outputData.CONDUCTOR_HV2) * 1.025);
    }
    public void bom_leads() {
        outputData.BOM_LEADS = outputData.BOM_CONDUCTOR_WT * 0.04;
    }
    public void bom_insulation_fg() {
        outputData.BOM_INSULATION_FG = 0.12 * outputData.BOM_CONDUCTOR_WT;
    }
    public void bom_connection_fg() {
        outputData.BOM_CONNECTION_FG = (4 * outputData.L_ACTIVE) / 1000;
    }
    public  void bom_insulation_cl_h() {
        outputData.BOM_INSULATION_CL_H = (0.025 * outputData.BOM_CONDUCTOR_WT);
    }
    public void bom_resin_v50() {
        outputData.BOM_RESIN_VT50 = (outputData.BOM_CORE + outputData.BOM_CORE_STEEL + outputData.BOM_CONDUCTOR_WT + outputData.BOM_LEADS + outputData.BOM_INSULATION_FG + outputData.BOM_CONNECTION_FG + outputData.BOM_INSULATION_CL_H) * 0.05;
    }
    public void bom_misc() {
        outputData.BOM_MISC = (outputData.BOM_CORE + outputData.BOM_CORE_STEEL + outputData.BOM_CONDUCTOR_WT + outputData.BOM_LEADS + outputData.BOM_INSULATION_FG + outputData.BOM_CONNECTION_FG + outputData.BOM_INSULATION_CL_H + outputData.BOM_RESIN_VT50) * 0.05;
    }
    public void bom_total_mass() {
        outputData.BOM_TOTAL_MASS = outputData.BOM_CORE + outputData.BOM_CORE_STEEL + outputData.BOM_CONDUCTOR_WT + outputData.BOM_LEADS + outputData.BOM_INSULATION_FG + outputData.BOM_CONNECTION_FG + outputData.BOM_INSULATION_CL_H + outputData.BOM_RESIN_VT50 + outputData.BOM_MISC + inputData.BOM_CRCA_ENCL;
    }

    public void mass() {
        outputData.MASS = outputData.CONDUCTOR_LV2 + outputData.CONDUCTOR_HV2;
    }

    public void mass_core_wdg() {
        outputData.MASS_CORE_WDG = (double)Math.round(1.135 * (outputData.TOTAL_CORE_MASS + outputData.MASS));
    }
    public void total_mass_w_o_encl() {
        outputData.TOTAL_MASS_W_O_ENCL = outputData.BOM_TOTAL_MASS - inputData.BOM_CRCA_ENCL;
    }

    public void rmc() {
        outputData.RMC = (double)Math.round((outputData.BOM_CORE * 260) + (outputData.BOM_CONDUCTOR_WT * 360) * 1.5);
    }
}