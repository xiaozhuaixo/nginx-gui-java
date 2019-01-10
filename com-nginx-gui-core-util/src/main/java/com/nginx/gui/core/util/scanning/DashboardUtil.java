package com.nginx.gui.core.util.scanning;

import org.hyperic.sigar.*;

/**
 * @author: hengbin_wu
 * @Date: 2019/1/10 10:21
 * @Description:
 */
public class DashboardUtil {

    /**
     * cpu 使用情况
     * @throws SigarException
     */
    public static void cpu() throws SigarException {
        Sigar sigar = new Sigar();
        CpuPerc[] cpuPercs = sigar.getCpuPercList();
        for(int i = 0 ; i < cpuPercs.length ; i++){
            CpuPerc cpuPerc = cpuPercs[i];
            System.out.println("CPU用户使用率:    " + CpuPerc.format(cpuPerc.getUser()));
            System.out.println("CPU系统使用率:    " + CpuPerc.format(cpuPerc.getSys()));
            System.out.println("CPU当前等待率:    " + CpuPerc.format(cpuPerc.getWait()));
            System.out.println("CPU当前错误率:    " + CpuPerc.format(cpuPerc.getNice()));
            System.out.println("CPU当前空闲率:    " + CpuPerc.format(cpuPerc.getIdle()));
            System.out.println("CPU总的使用率:    " + CpuPerc.format(cpuPerc.getCombined()));
        }
    }

    /**
     * 内存使用情况
     * @throws SigarException
     */
    public static void memory() throws SigarException{
        Sigar sigar = new Sigar();
        Mem mem = sigar.getMem();
        // 内存总量
        System.out.println("内存总量:    " + mem.getTotal() / 1024L + "K av");
        // 当前内存使用量
        System.out.println("当前内存使用量:    " + mem.getUsed() / 1024L + "K used");
        // 当前内存剩余量
        System.out.println("当前内存剩余量:    " + mem.getFree() / 1024L + "K free");
        Swap swap = sigar.getSwap();
        // 交换区总量
        System.out.println("交换区总量:    " + swap.getTotal() / 1024L + "K av");
        // 当前交换区使用量
        System.out.println("当前交换区使用量:    " + swap.getUsed() / 1024L + "K used");
        // 当前交换区剩余量
        System.out.println("当前交换区剩余量:    " + swap.getFree() / 1024L + "K free");
    }

    /**
     * 盘符使用情况
     * @throws SigarException
     */
    public static void file() throws SigarException{
        Sigar sigar = new Sigar();
        FileSystem fslist[] = sigar.getFileSystemList();
        for (int i = 0; i < fslist.length; i++) {
            System.out.println("分区的盘符名称" + i);
            FileSystem fs = fslist[i];
            // 分区的盘符名称
            System.out.println("盘符名称:    " + fs.getDevName());
            // 分区的盘符名称
            System.out.println("盘符路径:    " + fs.getDirName());
            System.out.println("盘符标志:    " + fs.getFlags());
            // 文件系统类型，比如 FAT32、NTFS
            System.out.println("盘符类型:    " + fs.getSysTypeName());
            // 文件系统类型名，比如本地硬盘、光驱、网络文件系统等
            System.out.println("盘符类型名:    " + fs.getTypeName());
            // 文件系统类型
            System.out.println("盘符文件系统类型:    " + fs.getType());
            FileSystemUsage usage = null;
            usage = sigar.getFileSystemUsage(fs.getDirName());
            switch (fs.getType()) {
                // TYPE_UNKNOWN ：未知
                case 0:
                    break;
                // TYPE_NONE
                case 1:
                    break;
                // TYPE_LOCAL_DISK : 本地硬盘
                case 2:
                    // 文件系统总大小
                    System.out.println(fs.getDevName() + "总大小:    " + usage.getTotal() + "KB");
                    // 文件系统剩余大小
                    System.out.println(fs.getDevName() + "剩余大小:    " + usage.getFree() + "KB");
                    // 文件系统可用大小
                    System.out.println(fs.getDevName() + "可用大小:    " + usage.getAvail() + "KB");
                    // 文件系统已经使用量
                    System.out.println(fs.getDevName() + "已经使用量:    " + usage.getUsed() + "KB");
                    double usePercent = usage.getUsePercent() * 100D;
                    // 文件系统资源的利用率
                    System.out.println(fs.getDevName() + "资源的利用率:    " + usePercent + "%");
                    break;
                // TYPE_NETWORK ：网络
                case 3:
                    break;
                // TYPE_RAM_DISK ：闪存
                case 4:
                    break;
                // TYPE_CDROM ：光驱
                case 5:
                    break;
                // TYPE_SWAP ：页面交换
                case 6:
                    break;
                default:
                    break;
            }
            System.out.println(fs.getDevName() + "读出：    " + usage.getDiskReads());
            System.out.println(fs.getDevName() + "写入：    " + usage.getDiskWrites());
        }
    }

    /**
     * 网络使用情况
     * @throws SigarException
     */
    public static void net() throws SigarException{
        Sigar sigar = new Sigar();
        String ifNames[] = sigar.getNetInterfaceList();
        for (int i = 0; i < ifNames.length; i++) {
            String name = ifNames[i];
            NetInterfaceConfig ifconfig = sigar.getNetInterfaceConfig(name);
            System.out.println("网络设备名:    " + name);
            System.out.println("IP地址:    " + ifconfig.getAddress());
            System.out.println("子网掩码:    " + ifconfig.getNetmask());
            if ((ifconfig.getFlags() & 1L) <= 0L) {
                System.out.println("!IFF_UP...skipping getNetInterfaceStat");
                continue;
            }
            NetInterfaceStat ifstat = sigar.getNetInterfaceStat(name);
            System.out.println(name + "接收的总包裹数:" + ifstat.getRxPackets());
            System.out.println(name + "发送的总包裹数:" + ifstat.getTxPackets());
            System.out.println(name + "接收到的总字节数:" + ifstat.getRxBytes());
            System.out.println(name + "发送的总字节数:" + ifstat.getTxBytes());
            System.out.println(name + "接收到的错误包数:" + ifstat.getRxErrors());
            System.out.println(name + "发送数据包时的错误数:" + ifstat.getTxErrors());
            System.out.println(name + "接收时丢弃的包数:" + ifstat.getRxDropped());
            System.out.println(name + "发送时丢弃的包数:" + ifstat.getTxDropped());
        }
    }
}
